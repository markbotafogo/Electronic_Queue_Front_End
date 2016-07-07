package ufrj.sps.electronicqueueclient;

import android.app.Application;

import java.util.ArrayList;

/**
 * Provides persistence, while the mUser remain in the app, saving the queues to be visualized in the
 * ObserverActivity and the mUser data if he/she has logged in (in case of users with privileges,
 * like controllers and owners).
 */

public class Cache extends Application{
	
	private ArrayList<Queue> mMemory;
	private boolean mRunning = false;
	private boolean mUserRegistered = false;
	private String mUserName;
	
    final static int NONE = 0;
	final static int OWNER = 1;
	final static int CONTROLLER = 2;
	
	private int mUser = NONE;
	private int mUserId = -1;
	
	public void onCreate(){
		
		super.onCreate();
		mMemory = new ArrayList<>();
		
	}
	
	boolean add(Queue queue){
		return mMemory.add(queue);
	}
	
	void addAll(ArrayList<Queue> queues){
		
		for(int i = 0; i < queues.size(); i++ ){
			
			Queue temp = this.searchById(queues.get(i));
			
			if (temp == null) mMemory.add(queues.get(i));
			
		}
	}
	
	Queue searchById(Queue queue){
		
		for(int i = 0; i < mMemory.size(); i++ ){
		
			if(queue.getID() == mMemory.get(i).getID()) return mMemory.get(i);
		
		}
		
		return null;
		
	}
	
	Queue searchById(int id){
		
		for(int i = 0; i < mMemory.size(); i++ ){
		
			if(id == mMemory.get(i).getID()) return mMemory.get(i);
		
		}
		
		return null;
		
	}
	
	ArrayList<Queue> searchByName(Queue queue){
		
		ArrayList<Queue> temp = new ArrayList<>();
		
		for(int i = 0; i < mMemory.size(); i++ ){
		
			if(queue.getName().equals(mMemory.get(i).getName())) temp.add(mMemory.get(i));
		
		}
		
		if (temp.isEmpty()) return null;
		
		return temp;

	}
	
	ArrayList<Queue> getAll(){
		return mMemory;
	}
	
	boolean isEmpty(){
		return mMemory.isEmpty();
	}

	/*TODO Rebuild this method returning an array of queues that have a ticket got by the user
	ArrayList<Ticket> getAllTickets(){

		return mMyTickets;

	}
	*/

    boolean isRunning() {
		return mRunning;
	}

    void setRunning(boolean running) {
		this.mRunning = running;
	}

    boolean isUserRegistered() {
		return mUserRegistered;
	}

    void setUserRegistered(boolean userRegistered) {
		this.mUserRegistered = userRegistered;
	}

    int getUser() {
		return mUser;
	}

    void setUser(int user) {
		this.mUser = user;
	}

    int getUserID() {
		return mUserId;
	}

    void setUserID(int userID) {
		this.mUserId = userID;
	}

    String getUserName() {
		return mUserName;
	}

    void setUserName(String userName) {
		this.mUserName = userName;
	}
	
    void logout(){

		this.mUser = Cache.NONE;
		this.mUserId = -1;
		this.mUserRegistered = false;
		this.mUserName = null;
		
	}
}
