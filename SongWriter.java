import java.util.ArrayList;
import java.util.Hashtable;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;

public class SongWriter{
    private Hashtable<Integer,String> pitchToNote;
    
    // The constructor of this class
    public SongWriter(){
        this.initPitchToNoteDictionary();
    }
    
    // This initialises the pitchToNote dictionary,
    // which will be used by you to convert pitch numbers
    // to note letters
    public void initPitchToNoteDictionary(){
        pitchToNote  = new Hashtable<Integer, String>();
        pitchToNote.put(60, "C");
        pitchToNote.put(61, "C#");
        pitchToNote.put(62, "D");
        pitchToNote.put(63, "D#");
        pitchToNote.put(64, "E");
        pitchToNote.put(65, "F");
        pitchToNote.put(66, "F#");
        pitchToNote.put(67, "G");
        pitchToNote.put(68, "G#");
        pitchToNote.put(69, "A");
        pitchToNote.put(70, "A#");
        pitchToNote.put(71, "B");
    }

    // This method converts a single MidiNote to its notestring representation
    public String noteToString(MidiNote note){
        String result = "";
        // TODO: Q4.a.
        int finalPitch = 0;
        if (note.getDuration() != 1){
            result += note.getDuration();
        }
        if (note.isSilent()){
            result += "P";
        }else{
            finalPitch = note.getPitch() - 12 * note.getOctave();
            result += pitchToNote.get(finalPitch);
        }
        return result;
    }

    // This method converts a MidiTrack to its notestring representation.
    // You should use the noteToString method here
    public  String trackToString(MidiTrack track){
        ArrayList<MidiNote> notes = track.getNotes();
        String result = "";
        int previous_octave = 0;
        MidiNote current_note;
        // TODO: Q4.b.

        /* 
        * A hint for octaves: if the octave of the previous MidiNote was -1
        * and the octave of the current MidiNote is +3, we will have 
        * to append ">>>>" to the result string.
        */
        
        for(int i = 0; i < track.getNotes().size(); i++){
            current_note = track.getNotes().get(i);
            int difference = current_note.getOctave() - previous_octave;
            if((difference != 0) && (current_note.isSilent() == false)){
                for (int j = 0; j < Math.abs(difference); j++){
                    if (difference > 0) result += ">";
                    else result += "<";
                }
            }
            result += noteToString(current_note);
            if(!current_note.isSilent()){
                previous_octave = current_note.getOctave();
            }
        }

        return result;
    }

    // TODO Q4.c.
    // Implement the void writeToFile( Song s1 , String file_path) method
    // This method writes the properties of the Song s1 object
    // and writes them into a file in the location specified by 
    // file_path. This file should have the same format as the sample
    // files in the 'data/' folder.
    
    public void writeToFile(Song s1 , String file_path){
        try {
            String text = "";
            SongWriter sw = new SongWriter();
            File file = new File(file_path);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            text = "bpm = " + s1.getBPM() + "\n";
            output.write(text);
            
            if(s1.getName() != null){
                text = "name = " + s1.getName() + "\n";
                output.write(text);
            }
            if(s1.getSoundbank() != ""){
                text = "soundbank = " + s1.getSoundbank() + "\n";
                output.write(text);
            }
            System.out.println(s1.getTracks().size());
            if(s1.getTracks().size() > 0){
                for (int i = 0; i < s1.getTracks().size(); i++){
                    text = "instrument = " + s1.getTracks().get(i).getInstrumentId() + "\n";
                    text = text + "track = " + sw.trackToString(s1.getTracks().get(i)) + "\n";
                    output.write(text);
                }
            }
            output.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args){
        // TODO: Q4.d.
        // Create a Song object
        Song mySong = new Song (); 
        // Load text file using the given song_filename, remember to 
        // catch the appropriate Exceptions, print meaningful messages!
        // e.g. if the file was not found, print "The file FILENAME_HERE was not found"
        String song_file_path = "./data/08.txt";
        mySong.loadFromFile(song_file_path);
        // call the revert method of the song object.
        mySong.revert();
        // Create a SongWriter object here, and call its writeToFile( Song s, String file_location) method.
        SongWriter writer = new SongWriter();
        int index = song_file_path.lastIndexOf("/");
        song_file_path = song_file_path.substring(0, index+1);
        song_file_path += mySong.getName();
        song_file_path += "_reverse.txt";
        writer.writeToFile(mySong, song_file_path);

    }
}