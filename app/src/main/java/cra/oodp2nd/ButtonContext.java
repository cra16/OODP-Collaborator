package cra.oodp2nd;

/**
 * Created by 현우 on 2015-06-07.
 */
public class ButtonContext {
    MainButtonState state;

    public ButtonContext()
    {
        state =null;

    }
    public void setState(MainButtonState state)
    {
        this.state=state;

    }
    public MainButtonState getState()
    {
        return this.state;
    }
}
