
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * used for communication among agents
 */
public class Mailer {

	private AtomicInteger totalNumIterations= new AtomicInteger(0);

	private AtomicInteger totalGain = new AtomicInteger(0);
	// maps between agents and their mailboxes
	private HashMap<Integer, List<Message>> map = new HashMap<>();
	
	// send message @m to agent @receiver
	public void send(int receiver, Message m) {
		
		List<Message> l = map.get(receiver);
		
		synchronized (l) {
			l.add(m);
		}
	}

	// agent @receiver reads the first message from its mailbox
	public Message readOne(int receiver) {
		
		List<Message> l = map.get(receiver);
		if (l.isEmpty()) {
			return null;
		}
		
		synchronized (l) {
			Message m = l.get(0);
			l.remove(0);
			return m;
		}
	}
	
	// only used for initialization
	public void put(int i) {
		List<Message> l= new ArrayList<Message>();
		this.map.put(i, l);
	}

	public void addToTotalGain(int agentGain)
	{
		totalGain.addAndGet(agentGain);
	}
	public void addToTotaliterations(int agentIterations)

	{
		totalNumIterations.addAndGet(agentIterations);
	}

	public int getTotalGain() {
		return totalGain.get();
	}

	public int getTotalIterations() {
		return totalNumIterations.get();
	}


}
