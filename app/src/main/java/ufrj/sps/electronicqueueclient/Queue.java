package ufrj.sps.electronicqueueclient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static ufrj.sps.electronicqueueclient.MainActivity.Port;
import static ufrj.sps.electronicqueueclient.MainActivity.TheIP;

/**
 * Implements the basic unit of the system. Queues can be put on the Observer for live watch,
 * created by a user with owner privileges, consumed by a user with controller or owner privileges
 * and can be searched in the remote database. The structure of this class was made to support the
 * existence of only one ticket per instance.
 */

public class Queue implements Comparable<Queue>{

    private final static int NUMBER = 2;
    private final static int PASSWORD = 3;

    private final static String mUrlEnter = "http://" + TheIP + Port + "/axis2/services/EQCloud/enterQueue?queue=";
    private final static String mUrlConsume = "http://" + TheIP + Port + "/axis2/services/EQCloud/consumeQueue?queue=";
    private final static String mUrlUpdate = "http://" + TheIP + Port + "/axis2/services/EQCloud/searchQueueId?search=";

	private String mName;
	private int mId;
	private int mLastPasswordCalled;
	private int mNextPasswordAvailable;
	private Ticket mTicket;
	private boolean mFollow = false;

    //Why did you created a nested class? Well... Ticket may become more complex in the future
	private class Ticket {

		private int mPassword;
		private Calendar mCreated;
		
		Ticket(int number){

			this.mPassword = number;
			this.mCreated = Calendar.getInstance();

		}
		
		int getPassword(){
			return this.mPassword;
		}
		
		Date getTimeOfCreation(){
			return mCreated.getTime();
		}
		
	}
	
	Queue(int id, String name, int actualNumber, int nextPassword){
		
		this.mId = id;
		this.mLastPasswordCalled = actualNumber;
		this.mName = name;
		this.mNextPasswordAvailable = nextPassword;
		
	}

	boolean createTicket(){
		
		if (mTicket != null) return false;
		
		HttpRequester httpRequester = new HttpRequester();
		httpRequester.setRequest(mUrlEnter + this.mId);
		String answer = httpRequester.getResponse();
		
		mTicket = new Ticket(Integer.parseInt(answer));
		
		return true;

	}
	
	int getTicket(){

        if(this.mTicket == null) return -1;
		return this.mTicket.getPassword();

	}

    Date getTicketTimeOfCreation(){
        return this.mTicket.getTimeOfCreation();
    }

	void removeTicket(){
		this.mTicket = null;
	}
	
	void update(){
		
		HttpRequester httpRequester = new HttpRequester();
		httpRequester.setRequest(mUrlUpdate + this.mId);
		ArrayList<String> answer = httpRequester.getResponses();
		String[] temp = answer.get(0).split(":"); 
		
		this.mLastPasswordCalled = Integer.parseInt(temp[NUMBER]);
		this.mNextPasswordAvailable = Integer.parseInt(temp[PASSWORD]);
		
	}
	
	boolean consume(){
			
			HttpRequester httpRequester = new HttpRequester();
			httpRequester.setRequest(mUrlConsume + this.mId);
			String answer = httpRequester.getResponse();
			
			return answer.equals("true");

	}
	
	String getName(){
		return this.mName;
	}
	
	int getID(){
		return this.mId;
	}
	
	int getNumber(){
		return this.mLastPasswordCalled;
	}
	
    int getNextPassword() {
		return mNextPasswordAvailable;
	}

	void follow(boolean follow) {
        this.mFollow = follow;
    }

	boolean isFollowing() {
        return this.mFollow;
    }

	@Override
	public int compareTo(Queue other) {
		return this.mName.compareTo(other.mName);
	}

}
