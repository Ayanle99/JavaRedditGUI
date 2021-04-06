import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CosNaming.IstringHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Reddit extends JFrame {

    // this is the main panel that will hold all the components
    static JPanel mainPanel = new JPanel();
    // this is the textfield that the user enters subreddits to search
    static JTextField textField = new JTextField(20);
    // this is the button to search subreddits
    static JButton search = new JButton("Search");
    // you can leave out/delete the numbers if you want,
    // numbers is just a way to number different threads in the sub we choose
    static JButton[] numbers = new JButton[28];
    // this is headlines
    static JButton[] headlines = new JButton[28];
    // this is to store votes/score each thread got the first 28 I chose
    static JLabel[] votes = new JLabel[28];
    // this is the title for headlines
    static JLabel title = new JLabel("\tHeadlines from r/");

    // this is the constructor for the class
    public Reddit(){

        // set the main panel to null in order for us to
        // set components where we want
        mainPanel.setLayout(null);
        // setup the UI for the GUI
        setUpComponents();

        // hide the title of the sub before we chose the sub,
        // after we search a sub, the title become visible again
        title.setVisible(false);

        // add actionlistener to the search button so that
        // we can get the data fromm the subreddit the user chose
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // set tthe title visible since now we know the name of the sub
                title.setVisible(true);
                // get the text from the textfield user typed
                String str = textField.getText();

                // clear the title in case there is previous text
                title.setText("");
                // set the title to the sub the user chose
                title.setText("\tHeadline from r/"+str);

                // get the data of the sub the user chose
                getSubReddit(str);


            }
        });


        // add mainpanel to the frame
        add(mainPanel);
        // set the title of the frame/window of our GUI
        setTitle("Reddit");
        // set the size of the window
        setSize(900,600);
        // whenever the user clicks the red circle in the top left, terminate the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set the window to visible otherwise we will not see anything
        setVisible(true);
    }

    // a method to set the User Interface of our GUI
    public static void setUpComponents(){

        // the label that will hold the reddit logo
        JLabel redditIcon = new JLabel(new ImageIcon());
        // set where the reddit logo will sit in the panel
        redditIcon.setBounds(10, 10, 100, 100);
        // pass the label to our method that will resize the logo
        resizeRedditLogo(redditIcon);
        // add the label to the main panel
        mainPanel.add(redditIcon);

        // set where the textfield will sit in the panel
        textField.setBounds(160, 20, 250, 30);
       // set the position for the search button as well
        search.setBounds(415, 23, 85, 25);
      // set the textfield background to dark red
        textField.setBackground(Color.decode("#8B0000"));

        // setting the background and the foreground of the search button
        search.setOpaque(true);
        search.setBorderPainted(false);
        search.setBackground(Color.decode("#8B0000"));
        search.setForeground(Color.WHITE);
        search.setBorder(BorderFactory.createRaisedBevelBorder());

        // this variable will incriment the y axis of our buttons,
        // otherwise our buttons will be on top of one another
        int y = 120;

        // loop through all the numbers array
        for (int i = 0; i < numbers.length;i++){
            // instantiate all the buttons here and set their value
            numbers[i] = new JButton(String.valueOf(i+1));
            // set where each button will sit on the panel,
            // the first button will sit (20 pixels from the left side of the screen,
            // y=120 pixels from the top, and the width of the button is 40 wide and 30 high)
            numbers[i].setBounds(20, y, 40,30);
            // set the background of all the buttonns to dark red
            numbers[i].setBackground(Color.decode("#8B0000"));
            //set all the buttons' foreground
            numbers[i].setOpaque(true);
            numbers[i].setForeground(Color.WHITE);
            numbers[i].setBorder(BorderFactory.createLoweredSoftBevelBorder());
            // after the first button position is set, we have
            // to add 40 pixels to the y which make the second button sit 40 pixels lower than the first,
            // the first button position is: (20,120,40, 30) and the second button is
            //                               (20,160,40,30) and so on, each time adding 40 pixels to the y
            y+= 40;

            numbers[i].setBorderPainted(false);
            // add the buttons to the main panel
            mainPanel.add(numbers[i]);
        }

        // we do exactly the same for the buttons that will hold the news headlines
        int y2 = 120;

        for (int i = 0; i < headlines.length;i++){

            headlines[i] = new JButton("");
            headlines[i].setBounds(70, y2, 650,30);
            headlines[i].setBackground(Color.decode("#8B0000"));
            headlines[i].setOpaque(true);
            headlines[i].setForeground(Color.WHITE);
            headlines[i].setBorder(BorderFactory.createLoweredSoftBevelBorder());
            y2+= 40;
            headlines[i].setBorderPainted(false);
            mainPanel.add(headlines[i]);

        }

        // same as the numbers array I described above.
        int y3 = 120;

        JLabel voteTitle = new JLabel("# Votes");
        voteTitle.setFont(new Font("Arial", Font.BOLD, 30));
        voteTitle.setForeground(Color.WHITE);
        voteTitle.setBounds(755, 70, 120, 30);
        mainPanel.add(voteTitle);
        for (int i = 0; i < votes.length;i++){

            votes[i] = new JLabel("");
            votes[i].setBounds(750, y3, 120,30);
            votes[i].setForeground(Color.WHITE);
            votes[i].setBorder(BorderFactory.createRaisedBevelBorder());
            y3+= 40;
            votes[i].setBorder(BorderFactory.createRaisedBevelBorder());
            mainPanel.add(votes[i]);
        }


        // set the text of the textfield to white
        textField.setForeground(Color.WHITE);

        // setting the title's position and adding it to the main panel
        title.setBorder(BorderFactory.createRaisedBevelBorder());
        title.setBounds(105, 70, 605, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        mainPanel.add(title);
        mainPanel.setBackground(Color.RED);
        mainPanel.add(textField);
        mainPanel.add(search);

    }

    // this method will fetch the data from reddig using jsoup
    public static void getSubReddit(String sub){


        try
        {
            // set the base url and add the sub name that was passed by the user from the textfield
            Document doc = Jsoup.connect("https://old.reddit.com/r/"+sub).get();
          // we are only interested the headline of each thread in the sub
            Elements el = doc.select("p.title");
            // and the score of each thread
            Elements score = doc.select("div.score.unvoted");

            // initilizing two indexes for the loop,
            // to iterate both the headline, and the score simultanously
            int i,j;

            // i keep tracks of the headline, while j keep tracks of score
            for (i=0,j=0;i<el.size() && j<score.size();i++,j++){

                // set headline text to headline buttons
                headlines[i].setText(el.get(i).text());
                // and score to votes buttons
                votes[j].setText(score.get(j).text());
            }

// catch any errors
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    // here is to resize the reddit logo
    public static void resizeRedditLogo(JLabel label){

        try {
            // set the size of the image
            ImageIcon imageIcon = new ImageIcon(new ImageIcon("../RedditProject/src/icon.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
            // set the image to the label we passed
            label.setIcon(imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    // this is the main method that will run the program
    public static void main(String[] args) {
        new Reddit();
    }

}
