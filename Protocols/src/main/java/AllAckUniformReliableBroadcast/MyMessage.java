package AllAckUniformReliableBroadcast;

/**
 * @author Kai Kang
 * @date 2023/11/27 3:45 上午
 */
public class MyMessage {
    private int msgID;
    private String content;
    private int senderProcID;

    public MyMessage(int msgID, String content, int procID) {
        this.msgID = msgID;
        this.content = content;
        this.senderProcID = procID;
    }

    public String getMsg() {
        return content;
    }

    public int getID() {
        return msgID;
    }


    public int getSenderProcID(){
        return senderProcID;
    }
}
