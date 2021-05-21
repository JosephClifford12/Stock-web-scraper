import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args)  {

        final String url = "https://www.imdb.com/chart/top/";



        try {
            final Document webDoc = Jsoup.connect(url).timeout(6000).get();  // get fetches and parses html
//          System.out.println(webDoc);
            Elements body  = webDoc.select("tbody.lister-list");
            //System.out.println(body.select("tr").size());

            for( Element e: body.select("tr")){
                String img = e.select("td.posterColumn img").attr("src");
                //System.out.println(img);

                String title  = e.select("td.posterColumn img").attr("alt");
                //System.out.println(title);

                String year = e.select("td.titleColumn span.secondaryInfo")
                                    .text().replaceAll("[^\\d]", "");       // replace braces
                //System.out.println(year);

                String rating = e.select("td.ratingColumn.imdbRating").text().trim();
                //System.out.println(rating);


                System.out.printf("Title: %s\nYear: %s\nRating: %s\nImage: %s\n\n", title, year, rating, img);
            }



        } catch ( Exception ex ){
            ex.printStackTrace();
        }














    }

}
