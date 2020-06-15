package controllerstests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controllers.PostTweetController;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samnishita
 */
public class PostTweetControllerTest {
    
    private PostTweetController ptc;
    public PostTweetControllerTest() {
         ptc = new PostTweetController();
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void shouldGetWord(){
        String word = "";
        try{
            word=ptc.getWord();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        //assertTrue(word.length()==0, "Word is empty");
        assertNotEquals(0, word.length(), "No word is produced");
    }
    
}
