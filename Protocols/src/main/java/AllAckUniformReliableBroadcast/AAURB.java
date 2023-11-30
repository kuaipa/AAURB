package AllAckUniformReliableBroadcast;


import java.util.*;

/**
 * @author Kai Kang
 * @date 2023/11/27 2:55 上午
 */

public class AAURB {
    // pending: beb delivered but no urb delivered
    // delivered: urb delivered
    // ack: all the processes which have already sent the msg
    // received: receive the msg from
    private List<HashSet<Integer>> received, delivered;
    private Hashtable<Integer, HashSet<Integer>> ack;
    private Hashtable<Integer, Boolean> correction;
    private int procAmount;

    private void initURB(List<MyMessage> msgList, List<Boolean> procFault) {
        received = new ArrayList<>();
        delivered = new ArrayList<>();
        ack = new Hashtable();
        correction = new Hashtable<>();
        procAmount = procFault.size();

        int len = msgList.size();
        for (int i = 0; i < len; i++) {
            ack.put(msgList.get(i).getID(), new HashSet<>());
        }
        for(int i = 0; i < procAmount; i++) {
            received.add(new HashSet<>());
            correction.put(i, procFault.get(i));
        }
    }

    private void urbBroadcast(List<MyMessage> msgList) {
        // start with a process having a msg need to deliver
        int len = msgList.size();
        for (int i = 0; i < len; i++) {
            // add the beginning process and msgid to ack
            MyMessage msg = msgList.get(i);
            received.get(msg.getSenderProcID()).add(msg.getSenderProcID());
            beBroadcast(msg, msg.getID(),false);
        }
    }

    private void beBroadcast(MyMessage msg, int curSenderProcID, boolean stopflag) {
        // if a process received a msg, it should bc to all others
        if (stopflag) {
            return;
        }
        stopflag = true;
        HashSet<Integer> sendedP = new HashSet<>();
        sendedP.add(curSenderProcID);
        for(int i = 0; i < received.size(); i++) {
            received.get(i).add(curSenderProcID);
            if (received.get(i).size() >= procAmount) {
                stopflag = false;
            }
        }
        for(int i = 0; i < procAmount; i++) {
            if (!sendedP.contains(i)) {
                beBroadcast(msg,curSenderProcID,stopflag);
            }
        }
    }

    private boolean canDeliver(int i) {
        return correction.get(i);
    }

    private List<String> urbDeliver(List<MyMessage> msg) {
        // all the correct processes will deliver the msg
        List<String> deliver = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < procAmount; i++) {
            if(canDeliver(i)) {
                list.add(i);
            }
        }
        for(int i = 0; i < msg.size(); i++) {
            deliver.add(msg.get(i).getMsg());
        }
        deliver.add("*");
        for (int i = 0; i < list.size(); i++) {
            deliver.add(list.get(i).toString());
        }
        return deliver;
    }

    public List<String> urb() {
        // create msg and process mock list
        List<MyMessage> msgList =mockMsg();
        List<Boolean> processFault = mockFault();
        initURB(msgList, processFault);
        urbBroadcast(msgList);
        return urbDeliver(msgList);
    }

    public int total() {
        return procAmount;
    }

    private List<MyMessage> mockMsg() {
        MyMessage myMessage = new MyMessage(1, "Test Message", 0);
        List<MyMessage> msgList = new ArrayList<>();
        msgList.add(myMessage);
        return msgList;
    }

    private List<Boolean> mockFault() {
        List<Boolean> processFault = new ArrayList<>();
        processFault.add(false);
        processFault.add(true);
        processFault.add(false);
        processFault.add(true);
        return processFault;
    }
}
