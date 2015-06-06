package cra.oodp2nd;

import android.database.Cursor;

import java.util.List;

/**
 * Created by wbqd on 15. 5. 16..
 */
public class TaskJob extends AbstractJob {
    private String assignee;
    private List<SubTaskJob> subTaskArray;
    private boolean isFinished;

    public TaskJob(Cursor result) {
        super(result);
//        setAssignee(assignee);
//        setIsFinished(isFinished);
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public List<SubTaskJob> getSubTaskArray() {
        return subTaskArray;
    }

    public void setSubTaskArray(List<SubTaskJob> subTaskArray) {
        this.subTaskArray = subTaskArray;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
