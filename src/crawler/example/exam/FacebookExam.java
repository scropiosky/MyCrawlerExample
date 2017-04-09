package crawler.example.exam;

import com.github.abola.crawler.CrawlerPack;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 練習：取得任一篇或多篇文章的 Reactions 總數
 *
 *
 * 重點
 * 1. 先利用Graph Api調整出需要的資料
 * 2. 修改程式，使用爬蟲包取得資料
 * 3. 上傳至GitHub
 * 
 * @author Abola Lee
 *
 */
public class FacebookExam {
	
	public static void main(String[] args) {
		
		// 遠端資料路徑

		String uri = 
				"https://graph.facebook.com/v2.6"
				+ "/ETtoday/feed?fields=id,reactions.limit(0).summary(total_count)"
				+ "&access_token=1289426837805412%7Cae451649927ef2a59a839574cba8e175";


		Elements elems =
				CrawlerPack.start()
				.getFromJson(uri)
				.select("data");
		
		String output = "id,reactions\n";

		// 遂筆處理
		for( Element data: elems ){
			String id = data.select("id").text();
			String reactions = data.select("reactions").text();
			// FIXIT



			output += id + "," + reactions + "\n";
		}

		System.out.println( output );
	} 
}
