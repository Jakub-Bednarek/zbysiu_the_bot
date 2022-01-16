package bot.player.utility;

public class Activity {
    private int voiceTime;
    private int messages;
    private boolean grabbedDaily;

    public Activity(int voiceTime, int messages, boolean grabbedDaily) {
        this.voiceTime = voiceTime;
        this.messages = messages;
        this.grabbedDaily = grabbedDaily;
    }

    public int getVoiceTime(){
        return voiceTime;
    }

    public int getMessages() {
        return messages;
    }

    public boolean isGrabbedDaily() {
        return grabbedDaily;
    }

    public void incrementMessages(){
        messages++;
    }

    public void addVoiceTime(double timeToAdd){
        voiceTime += timeToAdd;
    }
}
