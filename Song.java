import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

public class Song{
    String myName;
    int myBeatsPerMinute;
    String mySoundbank;
    ArrayList<MidiTrack> myTracks;
    
    // The constructor of this class
    public Song(){
        myTracks = new ArrayList<MidiTrack>();
        myBeatsPerMinute = 200;
        mySoundbank = "";
        myName = "Default_Name";
    }

    // GETTER METHODS

    public String getName(){
       return myName;
    }

    public String getSoundbank(){
       return mySoundbank;
    }
    
    public int getBPM(){
        return myBeatsPerMinute;
    }

    public ArrayList<MidiTrack> getTracks(){
        return myTracks;
    }

    // TODO: Q3.a.
    // Implement void loadFromFile(String file_path) method
    // This method loads the properties and build the tracks of this
    // song object from a file in the location specified by 
    // file
    
    public void loadFromFile(String file_path) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(new File(file_path)));
            for (String x = reader.readLine(); x != null; x = reader.readLine()){
                String[] temp = x.split("=", 2);
                temp[0] = temp[0].trim();
                temp[1] = temp[1].trim();
                if (temp[0].equals("name")){
                    this.myName = temp[1];
                }else if (temp[0].equals("bpm")){
                    this.myBeatsPerMinute = Integer.parseInt(temp[1]);
                }else if (temp[0].equals("soundbank")){
                    //try{
                        this.mySoundbank = temp[1];
                    //}catch(javax.sound.midi.InvalidMidiDataException e){
                    //    System.out.println("cannot load soundbank");
                    //}
                }else if (temp[0].equals("track")){
                    if(myTracks.size() != 0){
                        this.myTracks.get(myTracks.size() - 1).loadNoteString(temp[1]);
                    }else{
                        MidiTrack newTrack = new MidiTrack(0);
                        newTrack.loadNoteString(temp[1]);
                        this.myTracks.add(newTrack);
                    }
                }else if (temp[0].equals("instrument")){
                    MidiTrack newTrack = new MidiTrack(Integer.parseInt(temp[1]));
                    this.myTracks.add(newTrack);
                }
            }
        }catch (IOException e){
            System.out.println("Failed loading file");
        }
    }

    public void revert(){
        for (int i = 0; i<myTracks.size(); i++){
            myTracks.get(i).revert();
        }
    }
}