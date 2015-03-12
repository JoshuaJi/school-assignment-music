import java.util.ArrayList;
import java.util.Hashtable;

public class MidiTrack{
    private Hashtable<Character,Integer> noteToPitch;

    private ArrayList<MidiNote> notes;
    private int instrumentId;
    
    // The constructor for this class
    public MidiTrack(int instrumentId){
        notes = new ArrayList<MidiNote>();
        this.instrumentId = instrumentId;
        this.initPitchDictionary();
    }

    // This initialises the noteToPitch dictionary,
    // which will be used by you to convert note letters
    // to pitch numbers
    public void initPitchDictionary(){
        noteToPitch  = new Hashtable<Character, Integer>();
        noteToPitch.put('C', 60);
        noteToPitch.put('D', 62);
        noteToPitch.put('E', 64);
        noteToPitch.put('F', 65);
        noteToPitch.put('G', 67);
        noteToPitch.put('A', 69);
        noteToPitch.put('B', 71);
    }

    // GETTER METHODS
    public ArrayList<MidiNote> getNotes(){
        return notes;
    }
    
    public int getInstrumentId(){
        return instrumentId;
    }
    
    // This method converts notestrings like
    // <<3E3P2E2GP2EPDP8C<8B>
    // to an ArrayList of MidiNote objects 
    // ( the notes attribute of this class )
    public void loadNoteString(String notestring){
        // convert the letters in the notestring to upper case
        notestring = notestring.toUpperCase();
        int duration = 0;
        int pitch = 0;
        int octave = 0;
        
        // TODO: Q2. implement this method
        // Q2.a. Notes
        // Q2.b. Pauses
        // Q2.c. Durations
        // Q2.d. Octaves
        // Q2.e. Flat and sharp notes
        // Hint1: use a for loop with conditional statements
        // Hint2: Use the get method of the noteToPitch object (Hashtable class)
        
        MidiNote lastNote = new MidiNote(0, 0);
        for (int i = 0; i < notestring.length(); i++) {
            if((notestring.charAt(i) == 'A')||(notestring.charAt(i) == 'B')||(notestring.charAt(i) == 'C')||(notestring.charAt(i) == 'D')||(notestring.charAt(i) == 'E')||(notestring.charAt(i) == 'F')||(notestring.charAt(i) == 'G')||(notestring.charAt(i) == 'P')){
                if(notestring.charAt(i) == 'P'){
                    MidiNote temp = new MidiNote(0, duration);
                    temp.setSilent(true);
                    lastNote = temp;
                    notes.add(temp);
                }else{
                    MidiNote temp = new MidiNote(noteToPitch.get(notestring.charAt(i)) + 12*octave, duration);
                    lastNote = temp;
                    notes.add(temp);
                }
                duration = 0;
                pitch = 0;
            }else if((notestring.charAt(i) >= '0') && (notestring.charAt(i) <= '9')){
                duration = duration * 10 + Character.getNumericValue(notestring.charAt(i));
            }else if((notestring.charAt(i) == '>') || (notestring.charAt(i) == '<')){
                if(notestring.charAt(i) == '>') octave++;
                else octave--;
            }else if ((notestring.charAt(i) == '#') || (notestring.charAt(i) == '!')){
                if (notestring.charAt(i) == '#') lastNote.setPitch(lastNote.getPitch() + 1);
                else lastNote.setPitch(lastNote.getPitch() - 1);
            }
        }
        
        for (int i = 0; i < notes.size(); i++){
            System.out.println(notes.get(i).toString());
        }
    }

    public void revert(){
        ArrayList<MidiNote> reversedTrack = new ArrayList<MidiNote>();     
        for ( int i = notes.size() - 1; i >= 0; i--){
            MidiNote oldNote = notes.get(i);
            // create a newNote
            MidiNote newNote = new MidiNote(oldNote.getPitch(), oldNote.getDuration());
            
            // check if the note was a pause
            if(oldNote.isSilent()){
                newNote.setSilent(true);
            }
             
            // add the note to the new arraylist
            reversedTrack.add(newNote);
        }
        notes = reversedTrack;
    }

    // This will only be called if you try to run this file directly
    // You may use this to test your code.
    public static void main(String[] args){
        String notestring = "<<3E3P2E2GP2EPDP8C<8B>3E3P2E2GP2EPDP8C<8B>";

        // Build the MidiTrack object
        // Build a MusicInterpreter and set a playing speed
        // Load the track and play it
        // Close the player so that your program terminates
        
    }
}
