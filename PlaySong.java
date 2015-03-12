import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PlaySong{
    public static void main( String[] args){
        MusicInterpreter myMusicPlayer = new MusicInterpreter();
        // uncomment this line to print the available instruments
        //System.out.println(myMusicPlayer.availableInstruments());

        // TODO: Q3. b
        String song_file_path = "./data/08.txt"; 
        Song mySong = new Song (); 

        mySong.loadFromFile(song_file_path);
        
        MusicInterpreter mi = new MusicInterpreter (); 
        // Load the Song and play it 
        mi.loadSong(mySong); 
        mi.play(); 
        // close the player so that your program terminates 
        mi.close();
    }
}