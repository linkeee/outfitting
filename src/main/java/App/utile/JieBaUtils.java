package App.utile;


import com.google.gson.Gson;
import com.huaban.analysis.jieba.JiebaSegmenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class JieBaUtils {

    private final static JieBaUtils Instance = new JieBaUtils();
    /**
     * 将停用词库中的停用词以list形式存入内存
     */
    private List<String> stpws = stpws();
    private Gson gson = new Gson();

    public static JieBaUtils getInstance() {
        return Instance;
    }

    private List<String> stpws() {
        InputStream inputStream = null;
        BufferedReader br = null;
        List<String> stpws = new ArrayList<>();
        try {
            inputStream = JieBaUtils.class.getClassLoader().getResourceAsStream("App/txt/stopwords.txt");
            assert inputStream != null;
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                stpws.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        stpws.replaceAll(String::toLowerCase);
        return stpws;
    }

    /**
     * 计算一个库中所有文档的tfidf值，并返回。
     *
     * @param documents 所有的文档索引和内容
     * @return 文档索引：文档tfidf
     */
    public Map<String, String> getDocumentsTfIdfMap(Map<String, String> documents) {
        Map<String, String> ret = new HashMap<>();
        for (Map.Entry<String, String> entry : documents.entrySet()) {
            String document = entry.getValue();
            List<String> dTokens = getTokens(document);

            Map<String, Double> doctfidf = tfidfCalculate(documents, dTokens);
            ret.put(entry.getKey(), gson.toJson(doctfidf));
            System.out.println("-------------------------------------------------------->>>文档" + entry.getKey() + ">>>计算完成");
        }
        System.out.println("------------------------------------------------------->>>计算完成");
        return ret;
    }

    /**
     * 返回文档索引和相应的价值，并按价值高低降序排列
     *
     * @param inputStr 检索字符串
     * @param indexAndTfIdfMap  {文档ID:{word1:TFIDF, word2: TFIDF, ...}}
     * @return 排序好的文档索引和价值
     */
    public Map<String, Double> getSortedRelativityMap(String inputStr, Map<String, String> indexAndTfIdfMap) {
        List<String> itokens = getTokens(inputStr);
        System.out.println("-------------------->>>输入>>>" + itokens);

        Map<String, Double> nonSortValueMap = new HashMap<>();
        for (Map.Entry<String, String> entry : indexAndTfIdfMap.entrySet()) {
            Map<String, Double> map1 = gson.fromJson(entry.getValue(), nonSortValueMap.getClass());

            double temp = 0;
            for (String itoken : itokens) {
                if (map1.containsKey(itoken)) {
                    nonSortValueMap.put(entry.getKey(), temp = temp + map1.get(itoken));
                }
            }
        }

        System.out.println("-------------------->>>未排序结果>>>" + nonSortValueMap.toString());
        Map<String, Double> sortValueMap = new LinkedHashMap<>();
        nonSortValueMap.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).forEachOrdered(x -> sortValueMap.put(x.getKey(), x.getValue()));
        System.out.println("-------------------->>>排序后结果>>>" + sortValueMap.toString());
        return sortValueMap;
    }

    /**
     * 将传入字符串分词，去除停用词后，以list返回。
     *
     * @param sentence 待分词字符串
     * @return 分词结果list。英文都转换为小写，inputStr也是用该方法进行分词的，所以可以忽略大小写进行检索
     */
    private List<String> getTokens(String sentence) {
        JiebaSegmenter jiebaSegmenter = new JiebaSegmenter();
        List<String> strings = jiebaSegmenter.sentenceProcess(sentence);
        strings.replaceAll(String::toLowerCase);

        List<String> stopwords = stpws;//这句话要改成从数据库读取stopwords
        strings.removeAll(stopwords);
        return strings;
    }

    /**
     * D : 总文档数.
     * Dt: 包含词语 t_{i} 的文件数目，如果该词语不在语料库中，
     * 就会导致被除数为零，因此一般情况下使用t_{i}+1.
     *
     * @param documents 所有文档对应的分词
     * @param stokens   其中一个待计算的文档对应的分词。需计算其tf，用这个作为基础计算其分词在所有文档中的tfidf
     * @return 每个文档中的单词的tfidf的值
     */
    private Map<String, Double> tfidfCalculate(Map<String, String> documents, List<String> stokens) {
        int D = documents.size();
        Map<String, Double> tf = tfCalculate(stokens);
        Map<String, Double> tfidf = new HashMap<>();
        for (String key : tf.keySet()) { // 循环一个文档内容分词list中的所有单词
            int Dt = 0; // Dt记录所有文档中包含该单词的文档个数，包含的越多该词的idf值越低
            for (Map.Entry<String, String> entry : documents.entrySet()) { // 循环所有的文档
                List<String> wordlist = getTokens(entry.getValue()); // 得到循环到的该文档内容的分词list
                if (wordlist.contains(key)) {
                    Dt++; // 循环到的该文档分词list如果包含待计算文档中的该词时，Dt+1
                }
            }
            if (Dt < 1)
                Dt = 1;
            double idfvalue = Math.log((double) D / Dt);
            tfidf.put(key, idfvalue * tf.get(key));
        }
        System.out.println(tfidf.toString());
        return tfidf;
    }

    /**
     * 计算文档中每个词的tf值-->一个单词的tf值 = 频次/总词数
     *
     * @param document 文档内容字符串的分词list
     * @return Map<String, Float> key是单词 value是tf值
     */
    private Map<String, Double> tfCalculate(List<String> document) {
        //存放（单词，单词数量）
        Map<String, Integer> dict = new HashMap<>();
        //存放（单词，单词词频）
        Map<String, Double> tf = new HashMap<>();
        int wordCount = 0;

        /*
          统计每个单词的数量，并存放到map中去
          便于以后计算每个单词的词频
          单词的tf=该单词出现的数量n/总的单词数wordCount
         */
        for (String word : document) {
            wordCount++;
            if (dict.containsKey(word)) {
                dict.put(word, dict.get(word) + 1);
            } else {
                dict.put(word, 1);
            }
        }

        for (Map.Entry<String, Integer> entry : dict.entrySet()) {
            double wordTf = (double) entry.getValue() / wordCount;
            tf.put(entry.getKey(), wordTf);
        }
        return tf;
    }

}

