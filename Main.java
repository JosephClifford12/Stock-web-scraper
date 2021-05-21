// We need to import the Jsoup.jar into file>projectstructure>libraries>java>G:\Jsoup

package WebScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

public class WebScrape {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);


        // sort by 'symbol', 'company', or 'price'
        String sSortOption = "";
        // display each page by 10, 20, 30, 50, 100
        int iDisplayNum = 10;
        int iPageNum = 1;

        String lines = "-----------------------------";
        System.out.print("\n\n\t\t\t\t\t" + lines +  "Welcome to stock Trading info" + lines +
                "\n\n\nWould you like to browse some stocks: ");
        boolean running = false;
        String answ = input.nextLine();

        if(answ.equals("y") || answ.equals("Y")) {
            running = true;
        }

        System.out.print("Enter a page number[1-331]: ");
        int response = input.nextInt();
        iPageNum = response;

        System.out.print("Enter # of stocks you want to display [10, 20, 30, 50, 100]: ");
        response = input.nextInt();
        System.out.println("\n\n");
        iDisplayNum =  response;
        int count = 0;

        while(running) {

            if (count > 0) {
                System.out.print("Enter a page number[1-331]: ");
                response = input.nextInt();
                iPageNum = response;

                System.out.print("Enter # of stocks you want to display [10, 20, 30, 50, 100]: ");
                response = input.nextInt();
                iDisplayNum = response;
                System.out.println("\n\n");
            }


            String url = "https://www.gurufocus.com/stock_list.php?m_country_o%5B%5D=USA&m_country%5B%5D=USA" +
                    "&p=" + (iPageNum - 1) + "&n=" + (iDisplayNum) + "";


            try {

                // Document object to hold html file and .get() to parse it
                Document webDoc = Jsoup.connect(url).get();
                //System.out.println(webDoc);

                // list of elements parsed elements
                Elements body = webDoc.select("tbody");         // from the "tbody" tag
                //System.out.println(body.select("tr").size());


//            Elements tr = body.select("tr.text");

                System.out.println(lines + lines + lines + lines);
                String st = "Name:";
                System.out.format("%-72s", st);
                st = "Ticker:";
                System.out.format("%-32s", st);
                st = "Price:";
                System.out.format("%-10s\n", st);


                // for all the elements in the list of tag<tbody> <tr></tr> </tbody>... of type <tr>
                for (Element ele : body.select("tr")) {


                    // Get the symbol
                    String sSymbol = ele.select("td").first().text().trim();
                    String sCompatibleSymbolLength = "";


                    // if symbol is less than 15 characters or doesn't have white space... it's compatible.
                    if ((sSymbol.length() <= 15) || (!sSymbol.matches("\\S"))) {

                        if(!(sSymbol.contains("NYSE"))) {
                            sCompatibleSymbolLength = sSymbol;
                        }
                    }


                    // Get the company
                    // If company name doesn't have white space... it's compatible.
                    String sCompanyName = ele.select("td.text").text().trim();
                    String sCompatibleCompanyLength = "";
                    if ((!sCompanyName.matches("\\S"))) {
                        sCompatibleCompanyLength = sCompanyName;
                    }


                    // Get the price
                    String sPrice =     // "tag.class + sibling tag + sibling tag's 3rd element.
                            ele.select("td.text + td + td:eq(2)").text().trim();

                    //System.out.println("Price: " + sPrice);

                    // Formatted output
                    System.out.format("%-70s\t%-20s\t\t\t%-10s\n", sCompatibleCompanyLength,
                                                            sCompatibleSymbolLength, sPrice);



                }

                System.out.println();
                System.out.printf(lines +"---------------PAGE:[%s]\tDISPLAYED:[%s]" + lines + "-----------------\n\n" , iPageNum, iDisplayNum);

            } catch (Exception e) {
                e.printStackTrace();
            }

            input.nextLine();
            System.out.print("Would you like to search another page?: ");

            answ = input.nextLine();

            if ( (answ.equals("y") )|| (answ.equals("Y")) ) {
                count++;
            } else {
                System.out.println("Program Finished.");
                running = false;

            }
            count++;
        }

    }


}
