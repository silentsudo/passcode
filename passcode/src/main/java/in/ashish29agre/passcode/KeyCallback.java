package in.ashish29agre.passcode;

/**
 * Created by Ashish on 25/12/15.
 */
public interface KeyCallback {

    public void onPrevious();
    public void onNext(char value);
    public void onDone();
    public void onDelete();

}
