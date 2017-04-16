package crawler.example.integration;

import com.github.abola.crawler.CrawlerPack;
import com.mashape.unirest.http.Unirest;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * Created by Abola Lee on 2016/7/10.
 */
public class FBElasticImport {

    static String elasticHost = "localhost" ;
    static String elasticPort = "9200" ;
    static String elasticIndex = "tb_fgo_1";
    static String elasticIndexType = "data";
    static String pageName = "FateGO.TW";
    static long start = 1492345280;
    static int days = 60;

    public static void main(String[] args) {

        for (long datatime = start ; datatime > start-86400*days ;datatime-=3600*8) {
            String uri =
                    "https://graph.facebook.com/v2.8"
                            + "/"+pageName +"/feed?fields=comments,from,created_time&since="+(datatime-3600*8)+"&until="+datatime
                            + "&access_token=1289426837805412%7Cae451649927ef2a59a839574cba8e175";


            try {
                PrintStream out = new PrintStream(new
                        BufferedOutputStream(new
                        FileOutputStream("c:\\fgo_1.txt", true)));


                Elements elems =
                        CrawlerPack.start()
                                .getFromJson(uri)
                                .select("data:has(created_time)");
//              System.out.println(elems);
//              String output = "id,名稱,按讚數,討論人數\n";
//                System.out.println(elems.size());
//                // 遂筆處理
                for (Element data : elems) {

//                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String created_time = data.select("created_time").text();
                    String id = data.select("from > id").text();
//                    String idtime = data.select("from > id,data data created_time").text();
//                    String message = data.select("message").text();
//                    String likes = data.select("likes > summary > total_count").text();
//                    String comments = data.select("comments > summary > total_count").text();
//                  String name = data.select("name").text();
//                  String likes = data.select("likes").text();
//                  String talking_about_count = data.select("talking_about_count").text();
//
//                  output += id+",\""+name+"\","+likes+","+talking_about_count+"\n";
//                    System.out.println(created_time);
//                    System.out.println(id);
//                    System.out.println(message);
//                    System.out.println(likes);
                    // Elasticsearch data format


//                    String elasticJson = "{" +
//                            "\"created_time\":\"" + created_time + "\"" +
//////                            ",\"message\":\"" + message + "\"" +
//////                            ",\"likes\":" + likes +
//                            ",\"id\":\"" + id + "\"" +
//                            ",\"pagename\":\"" + pageName + "\"" +
//////                            ",\"comments\":" + comments +
//                            "}";


                    String elasticJson =  created_time + "," + id +"," + pageName +"\n";

//                    System.out.println(
//                            // curl -XPOST http://localhost:9200/pm25/data -d '{...}'
//                            sendPost("http://" + elasticHost + ":" + elasticPort
//                                            + "/" + elasticIndex + "/" + elasticIndexType
//                                    , elasticJson));
//                System.out.println(elasticJson);
//                    System.setOut(out); // 轉換輸出流
//                    System.out.println(elasticJson); // 將輸出傳進文件
                    out.println(elasticJson);

                }

                out.close();

            }catch(Exception e){}

        }
    }



    /***
     * NVL
     * if arg0 isNull then return arg1
     */
    static public <T> T nvl(T arg0, T arg1) {
        return (arg0 == null)?arg1:arg0;
    }

    static String sendPost(String url, String body){
        try{
            return Unirest.post(url)
                    .header("content-type", "text/plain")
                    .header("cache-control", "no-cache")
                    .body(body)
                    .asString().getBody();

        }catch(Exception e){return "Error:" + e.getMessage();}
    }
}


