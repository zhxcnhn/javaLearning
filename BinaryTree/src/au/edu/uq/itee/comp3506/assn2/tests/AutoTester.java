package au.edu.uq.itee.comp3506.assn2.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.edu.uq.itee.comp3506.assn2.api.TestAPI;
import au.edu.uq.itee.comp3506.assn2.entities.BinarySearchTree;
import au.edu.uq.itee.comp3506.assn2.entities.CallRecord;
import au.edu.uq.itee.comp3506.assn2.entities.SwitchTree;
import au.edu.uq.itee.comp3506.assn2.entities.TreeNode;

/**
 * Hook class used by automated testing tool. Methods and constructor have been
 * implemented for all of the 6 search.
 * 
 * Space efficiency: S(n) = O(log n)
 * 
 * @author Haoxiang Zheng
 */
public final class AutoTester implements TestAPI {

	private List<Long> list;
	private List<CallRecord> cRecord;
	private BinarySearchTree tree;
	private SwitchTree swTree;
	private List<Integer> faulty;
	private int maxSwitch;
	private int minSwitch;
	private int maxCount;
	private int minCount;
	private int connectionCount;
	boolean flag;
	
	/**
	 * Initialize a new AutoTester. The data-sets will be retrieved from switches file and 
	 * call-record file, and save them into binary tree separately.
	 * 
	 * Run-time efficiency: T(n) = O(n^2)
	 * 
	 */
	public AutoTester() {
		tree = new BinarySearchTree();
		swTree = new SwitchTree();
		
		/*******************Switch file input*********************/
		File file = new File("data/switches.txt");
		try {
			Scanner in = new Scanner(file);
			String identifier = in.nextLine().trim();
			while(in.hasNextLine()){
				identifier = in.nextLine().trim();
				if(!identifier.equals("")){
					int recordId = randomNum(); 
					TreeNode<Integer> switchId = new TreeNode<Integer>(recordId, Integer.parseInt(identifier));
					swTree.insert(switchId);
						}
					}				
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*******************CallRecord file input*********************/
		try {
			File inputFile = new File("data/call-records.txt");
			Scanner input = new Scanner(inputFile);
			
			while(input.hasNextLine()){
				String line = input.nextLine().trim();
				if (!line.equals("")){
					String receive [] = line.split(" ");		
					int arrayCount = 0;
					for(int j = 0; j < receive.length; j++){
						if (!receive[j].trim().equals("")){
							arrayCount++;
						}
					}
					String retrieve [] = new String[arrayCount];
					arrayCount = 0;
					for(int j = 0; j < receive.length; j++){
						if (!receive[j].trim().equals("")){
							retrieve[arrayCount] = receive[j].trim();
							arrayCount++;
						}
					}
					long dialler = 0;
					long receiver = 0;
					int diallerSwitch = 0;
					int receiverSwitch = 0;
					List<Integer> connectionPath = new ArrayList<Integer>();
					LocalDateTime timeStamp = null;
					int count = 0;
					
					for (int i = 0; i < retrieve.length; i++){
						if(i == 0){
							if(isNumericTen(retrieve[i].trim())){
								dialler = Long.parseLong(retrieve[i].trim());
							}
							else{
								count++;
							}
						}
						else if(i == 1){
							if(!retrieve[i].trim().equals("")){
								if(isNumericFive(retrieve[i].trim())){
									flag = false;
									int temp = Integer.parseInt(retrieve[i].trim());
									if(isInSwitchFile(swTree.getRoot(), temp)){
										diallerSwitch = Integer.parseInt(retrieve[i].trim());
									}
									else{
										count++;
									}
								}
								else{
									count++;
								}
							}
							else{
								count++;
							}
																		
						}
						else if(i == (retrieve.length - 3)){
							if(!retrieve[i].trim().equals("")){
								if(isNumericFive(retrieve[i].trim())){
									flag = false;
									int temp = Integer.parseInt(retrieve[i].trim());
									if(isInSwitchFile(swTree.getRoot(), temp)){
										receiverSwitch = Integer.parseInt(retrieve[i].trim());
									}
									else {
										count++;
									}
								}
								else {
									count++;
								}
							}
							else {
								count++;
							}								
						}
						else if(i == (retrieve.length - 2)){
							if(isNumericTen(retrieve[i].trim())){
								receiver = Long.parseLong(retrieve[i].trim());
							}
							else{
								count++;
							}
						}
						else if(i == (retrieve.length - 1)){
							if (isValidDate(retrieve[i].trim())){
								timeStamp = LocalDateTime.parse(retrieve[i].trim());
							}
							else{
								count++;
							}
						}
						else {
							if (!retrieve[i].trim().equals("")){
								if(isNumericFive(retrieve[i].trim())){
									flag = false;
									int temp = Integer.parseInt(retrieve[i].trim());
									if(isInSwitchFile(swTree.getRoot(), temp)){
											//for case A B C
											if(i == retrieve.length-4 && i == 2 && temp != Integer.parseInt(retrieve[retrieve.length - 3].trim())
													&& temp != Integer.parseInt(retrieve[1].trim())){
												count++;
												
											}
											else{
												connectionPath.add(temp);
												if(connectionPath.size() == 1){
													if(connectionPath.get(0) != diallerSwitch){
														count++;
													}
												}
												
											}
									}
									else{
										count++;
									}
								}
								else{
									count++;
								}
							}							
						}
					}
					
					if(count == 0){
						boolean in = this.findDuplicateElement(connectionPath);
						if(!in && receiverSwitch != 0){
							CallRecord call = new CallRecord(dialler, receiver, 
									diallerSwitch, receiverSwitch, connectionPath, timeStamp);
							int recordId = randomNum();
							TreeNode<CallRecord> record = new TreeNode<CallRecord>(recordId, call);
							tree.insert(record);
						}
					}					
				}			
			}
			input.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return List of all the phone numbers called by dialler.
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 * @throws IOException 
	 */
	@Override
	public List<Long> called(long dialler) {
		list = new ArrayList<Long>();
		
		List<Long> reveiverList = search1(tree.getRoot(), dialler);
		return reveiverList;
		
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree and
	 * add detected faulty into list and return them.
	 * 
	 * @param node The root node of CallRecord tree
	 * @param dialler The phone number that made the calls.
	 * @return List of all the phone numbers that called the dialler
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 */
	private List<Long> search1(TreeNode<CallRecord> node, Long dialler){  
        if(node.getValue().getDialler() == dialler){
        	list.add(node.getValue().getReceiver());
        }
        if(node.getLeftNode() != null)  
            this.search1(node.getLeftNode(), dialler);  
        if(node.getRightNode() != null)  
            this.search1(node.getRightNode(), dialler);
		return list;  
    } 

	/**
	 * Tests search 1 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers called by dialler between start and end time.
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 */
	@Override
	public List<Long> called(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		list = new ArrayList<Long>();
		
		List<Long> reveiverList = search1LDT(tree.getRoot(), dialler, startTime, endTime);
		return reveiverList;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree and
	 * add detected faulty into list and return them.
	 * 
	 * @param node The root node of CallRecord tree
	 * @param dialler The phone number that made the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the dialler between start and end time.
	 *         The list will contain duplicates of the receiver if the dialler called the receiver multiple times.
	 */
	private List<Long> search1LDT(TreeNode<CallRecord> node, long dialler, LocalDateTime startTime, 
			LocalDateTime endTime){  
        if(node.getValue().getDialler() == dialler && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        	list.add(node.getValue().getReceiver());
        }
        
        if(node.getLeftNode() != null)  
            this.search1LDT(node.getLeftNode(), dialler, startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search1LDT(node.getRightNode(), dialler, startTime, endTime);
		return list;  
    }

	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @return List of all the phone numbers that called the receiver.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver) {
		list = new ArrayList<Long>();
		
		List<Long> diallerList = search2(tree.getRoot(), receiver);
		return diallerList;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree and
	 * add detected faulty into list and return them.
	 * 
	 * @param node The root node of CallRecord tree
	 * @param receiver The phone number that received the calls.
	 * @return List of all the phone numbers that called the receiver
	 * 		 The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	private List<Long> search2(TreeNode<CallRecord> node, long receiver){  
        if(node.getValue().getReceiver() == receiver){
        	list.add(node.getValue().getDialler());
        }
        if(node.getLeftNode() != null)  
            this.search2(node.getLeftNode(), receiver);  
        if(node.getRightNode() != null)  
            this.search2(node.getRightNode(), receiver);
		return list;  
    }

	/**
	 * Tests search 2 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param receiver The phone number that received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the receiver between start and end time.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	@Override
	public List<Long> callers(long receiver, LocalDateTime startTime, LocalDateTime endTime) {
		list = new ArrayList<Long>();
		
		List<Long> diallerList = search2LDT(tree.getRoot(), receiver, startTime, endTime);
		return diallerList;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree and
	 * add detected faulty into list and return them.
	 * 
	 * @param node The root node of CallRecord tree
	 * @param receiver The phone number that received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of all the phone numbers that called the receiver between start and end time.
	 *         The list will contain duplicates of the caller if they called the receiver multiple times.
	 */
	private List<Long> search2LDT(TreeNode<CallRecord> node, long receiver, LocalDateTime startTime, 
			LocalDateTime endTime){  
        if(node.getValue().getReceiver() == receiver && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        	list.add(node.getValue().getDialler());
        }
        if(node.getLeftNode() != null)  
            this.search2LDT(node.getLeftNode(), receiver, startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search2LDT(node.getRightNode(), receiver, startTime, endTime);
		return list;  
    }

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler) {
		faulty = new ArrayList<Integer>();
		
		List<Integer> connectionFault = search3Connection(tree.getRoot(), dialler);
		return connectionFault;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree
	 * 
	 * @param node The root node of CallRecord tree
	 * @param dialler The phone number that should have made the calls.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found in all record
	 */
	private List<Integer> search3Connection(TreeNode<CallRecord> node, long dialler){	
		if(node.getValue().getDialler() == dialler){
        	if(node.getValue().getConnectionPath().size() != 0){
        		if(node.getValue().getDiallerSwitch() != node.getValue().getConnectionPath().get(0)){
        			faulty.add(node.getValue().getConnectionPath().get(0));
        		}
        		int l = node.getValue().getConnectionPath().size();
        		if(node.getValue().getReceiverSwitch() != node.getValue().getConnectionPath().get(l-1)){
        			faulty.add(node.getValue().getConnectionPath().get(l-1));
        		}  
        	}
        	else if(node.getValue().getConnectionPath().size() == 0){
        		faulty.add(node.getValue().getDiallerSwitch());
        	}
        }
		
        if(node.getLeftNode() != null)  
            this.search3Connection(node.getLeftNode(), dialler);  
        if(node.getRightNode() != null)  
            this.search3Connection(node.getRightNode(), dialler);
		
		return faulty;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param dialler The phone number that initiated the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findConnectionFault(long dialler, LocalDateTime startTime, LocalDateTime endTime) {
		faulty = new ArrayList<Integer>();
		
		List<Integer> connectionFault = search3ConnectionLDT(tree.getRoot(), dialler, startTime, endTime);
		return connectionFault;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree
	 * 
	 * @param node The root node of CallRecord tree
	 * @param dialler The phone number that should have made the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	private List<Integer> search3ConnectionLDT(TreeNode<CallRecord> node, long dialler, LocalDateTime startTime, LocalDateTime endTime){	
		if(node.getValue().getDialler() == dialler){
        	if(node.getValue().getConnectionPath().size() != 0 && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
            		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        		if(node.getValue().getDiallerSwitch() != node.getValue().getConnectionPath().get(0)){
        			faulty.add(node.getValue().getConnectionPath().get(0));
        		}
        		int l = node.getValue().getConnectionPath().size();
        		if(node.getValue().getReceiverSwitch() != node.getValue().getConnectionPath().get(l-1)){
        			faulty.add(node.getValue().getConnectionPath().get(l-1));
        		}  
        	}
        	else if(node.getValue().getConnectionPath().size() == 0 && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
            		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        		faulty.add(node.getValue().getDiallerSwitch());
        	}
        }
		
        if(node.getLeftNode() != null)  
            this.search3ConnectionLDT(node.getLeftNode(), dialler, startTime,endTime);  
        if(node.getRightNode() != null)  
            this.search3ConnectionLDT(node.getRightNode(), dialler, startTime, endTime);
		
		return faulty;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @return The list of identifiers of the faulty switches or an empty list if no fault was found.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever) {
		faulty = new ArrayList<Integer>();
		
		List<Integer> connectionFault = search3Receiving(tree.getRoot(), reciever);
		return connectionFault;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree
	 * 
	 * @param node The root node of CallRecord tree
	 * @param receiver The phone number that should have received the calls.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found in all record
	 */
	private List<Integer> search3Receiving(TreeNode<CallRecord> node, long reciever){	
		if(node.getValue().getReceiver() == reciever){
        	if(node.getValue().getConnectionPath().size() != 0){
        		if(node.getValue().getDiallerSwitch() != node.getValue().getConnectionPath().get(0)){
        			faulty.add(node.getValue().getConnectionPath().get(0));
        		}
        		int l = node.getValue().getConnectionPath().size();
        		if(node.getValue().getReceiverSwitch() != node.getValue().getConnectionPath().get(l-1)){
        			faulty.add(node.getValue().getConnectionPath().get(l-1));
        		}
        	}
        	else if(node.getValue().getConnectionPath().size() == 0){
        		faulty.add(node.getValue().getDiallerSwitch());
        	}
        }
		
        if(node.getLeftNode() != null)  
            this.search3Receiving(node.getLeftNode(), reciever);  
        if(node.getRightNode() != null)  
            this.search3Receiving(node.getRightNode(), reciever);
		
		return faulty;
	}

	/**
	 * Tests search 3 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param reciever The phone number that should have received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	@Override
	public List<Integer> findReceivingFault(long reciever, LocalDateTime startTime, LocalDateTime endTime) {
		faulty = new ArrayList<Integer>();
		
		List<Integer> connectionFault = search3ReceivingLDT(tree.getRoot(), reciever, startTime, endTime);
		return connectionFault;
	}
	
	/**
	 * Using recursion to go through each node of the callRecord tree
	 * 
	 * @param node The root node of CallRecord tree
	 * @param reciever The phone number that should have received the calls.
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The list of identifiers of the faulty switches;
	 *         or an empty list if no fault was found between start and end time.
	 */
	private List<Integer> search3ReceivingLDT(TreeNode<CallRecord> node, long reciever, LocalDateTime startTime, LocalDateTime endTime){	
		if(node.getValue().getReceiver() == reciever && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        	if(node.getValue().getConnectionPath().size() != 0){
        		if(node.getValue().getDiallerSwitch() != node.getValue().getConnectionPath().get(0)){
        			faulty.add(node.getValue().getConnectionPath().get(0));
        		}
        		int l = node.getValue().getConnectionPath().size();
        		if(node.getValue().getReceiverSwitch() != node.getValue().getConnectionPath().get(l-1)){
        			faulty.add(node.getValue().getConnectionPath().get(l-1));
        		}  
        	}
        	else if(node.getValue().getConnectionPath().size() == 0 && node.getValue().getTimeStamp().compareTo(startTime) >= 0 
            		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        		faulty.add(node.getValue().getDiallerSwitch());
        	}
        }
		
        if(node.getLeftNode() != null)  
            this.search3ReceivingLDT(node.getLeftNode(), reciever, startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search3ReceivingLDT(node.getRightNode(), reciever, startTime, endTime);
		
		return faulty;
	}

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n^2)
	 * 
	 * @return The identifier of the switch that had the most connections.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections() {
		maxSwitch = 0;
		maxCount = 0;
			
		boolean b = switches4(swTree.getRoot());
		
		return maxSwitch;
	}
	
	/**
	 * Iterate binary tree using recursion to get each iteration of a switch
	 * 
	 * @param node The root node of binary tree
	 * @return the times that the switch has been counted
	 */
	private boolean switches4(TreeNode<Integer> node){
		connectionCount = 0;
		int times = search4(tree.getRoot(), node.getValue());
		
        if(times > maxCount){
        	maxSwitch = node.getValue();
			maxCount = times;
        }
        if(times == maxCount){
			if(maxSwitch > node.getValue()){
				maxSwitch = node.getValue();
			}
		}
        if(node.getLeftNode() != null)  
            this.switches4(node.getLeftNode());  
        if(node.getRightNode() != null)  
            this.switches4(node.getRightNode());
		
        return true;
	}
	
	/**
	 * Iterate binary tree using recursion to get the count of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param iSwitch Input switch
	 * @return the times that the switch has been counted
	 */
	private int search4(TreeNode<CallRecord> node, int iSwitch){  
        for (int i = 0; i < node.getValue().getConnectionPath().size(); i++){
        	if(node.getValue().getConnectionPath().get(i) == iSwitch){	
        		connectionCount++;
        	}
        }
        if(node.getLeftNode() != null)  
            this.search4(node.getLeftNode(), iSwitch);  
        if(node.getRightNode() != null)  
            this.search4(node.getRightNode(), iSwitch);
		return connectionCount;  
    } 

	/**
	 * Tests search 4 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n^2)
	 * 
	 * @return The identifier of the switch that had the most connections.
	 *         If multiple switches have the most connections, the smallest switch identifier is returned.
	 */
	@Override
	public int maxConnections(LocalDateTime startTime, LocalDateTime endTime) {
		maxSwitch = 0;
		maxCount = 0;

		boolean b = switches4LDT(swTree.getRoot(), startTime, endTime);		
		
		return maxSwitch;
	}
	
	/**
	 * Iterate binary tree using recursion to get each iteration of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return the times that the switch has been counted between start and end time.
	 */
	private boolean switches4LDT(TreeNode<Integer> node, LocalDateTime startTime, LocalDateTime endTime){
		connectionCount = 0;
		int times = search4LDT(tree.getRoot(), node.getValue(), startTime, endTime);
        if(times > maxCount){
        	maxSwitch = node.getValue();
			maxCount = times;
        }
        if(times == maxCount){
			if(maxSwitch > node.getValue()){
				maxSwitch = node.getValue();
			}
		}
        if(node.getLeftNode() != null)  
            this.switches4LDT(node.getLeftNode(), startTime, endTime);  
        if(node.getRightNode() != null)  
            this.switches4LDT(node.getRightNode(), startTime, endTime);
		
        return true;
	}
	
	/**
	 * Iterate binary tree using recursion to get the count of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param iSwitch Input switch
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return the times that the switch has been counted between start and end time.
	 */
	private int search4LDT(TreeNode<CallRecord> node, int iSwitch, LocalDateTime startTime, LocalDateTime endTime){  
        if(node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
        	for (int i = 0; i < node.getValue().getConnectionPath().size(); i++){
            	if(node.getValue().getConnectionPath().get(i) == iSwitch){	
            		connectionCount++;
            	}
            }
        }
		
        if(node.getLeftNode() != null)  
            this.search4LDT(node.getLeftNode(), iSwitch, startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search4LDT(node.getRightNode(), iSwitch, startTime, endTime);
		return connectionCount;  
    }

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n^2)
	 * 
	 * @return The identifier of the switch that had the fewest connections.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections() {
		minSwitch = 0;
		minCount = 100000;
		
		boolean b = switches5(swTree.getRoot());
		
		return minSwitch;
	}
	
	/**
	 * Iterate binary tree using recursion to get each iteration of a switch
	 * 
	 * @param node The root node of binary tree
	 * @return the times that the switch has been counted
	 */
	private boolean switches5(TreeNode<Integer> node){
		connectionCount = 0;
		int times = search5(tree.getRoot(), node.getValue());
		
		if(times != 0){
			if(times < minCount){
				minSwitch = node.getValue();
				minCount = times;
			}
			if(times == minCount){
				if(minSwitch > node.getValue()){
					minSwitch = node.getValue();
				}
			}
		}
        
        if(node.getLeftNode() != null)  
            this.switches5(node.getLeftNode());  
        if(node.getRightNode() != null)  
            this.switches5(node.getRightNode());
		
        return true;
	}
	
	/**
	 * Iterate binary tree using recursion to get the count of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param iSwitch Input switch
	 * @return the times that the switch has been counted
	 */
	private int search5(TreeNode<CallRecord> node, int iSwitch){  
        for (int i = 0; i < node.getValue().getConnectionPath().size(); i++){
        	if(node.getValue().getConnectionPath().get(i) == iSwitch){	
        		connectionCount++;
        	}
        }
        if(node.getLeftNode() != null)  
            this.search5(node.getLeftNode(), iSwitch);  
        if(node.getRightNode() != null)  
            this.search5(node.getRightNode(), iSwitch);
		return connectionCount;  
    }
	

	/**
	 * Tests search 5 from the assignment specification.
	 * 
	 * Run-time efficiency: T(n) = O(n^2)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return The identifier of the switch that had the fewest connections between start and end time.
	 *         If multiple switches have the fewest connections, the smallest switch identifier is returned.
	 */
	@Override
	public int minConnections(LocalDateTime startTime, LocalDateTime endTime) {
		minSwitch = 0;
		minCount = 100000;
		boolean b = switches5LDT(swTree.getRoot(), startTime, endTime);
		
		return minSwitch;
	}
	
	/**
	 * Iterate binary tree using recursion to get each iteration of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return the times that the switch has been counted between start and end time.
	 */
	private boolean switches5LDT(TreeNode<Integer> node, LocalDateTime startTime, LocalDateTime endTime){
		connectionCount = 0;
		int times = search5LDT(tree.getRoot(), node.getValue(), startTime, endTime);
		if(times != 0){
			if(times < minCount){
				minSwitch = node.getValue();
				minCount = times;
			}
			if(times == minCount){
				if(minSwitch > node.getValue()){
					minSwitch = node.getValue();
				}
			}
		}
        if(node.getLeftNode() != null)  
            this.switches5LDT(node.getLeftNode(), startTime, endTime);  
        if(node.getRightNode() != null)  
            this.switches5LDT(node.getRightNode(), startTime, endTime);
		
        return true;
	}
	
	/**
	 * Iterate binary tree using recursion to get the count of a switch
	 * 
	 * @param node The root node of binary tree
	 * @param iSwitch Input switch
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return the times that the switch has been counted between start and end time.
	 */
	private int search5LDT(TreeNode<CallRecord> node, int iSwitch, LocalDateTime startTime, LocalDateTime endTime){  
		if(node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
			for (int i = 0; i < node.getValue().getConnectionPath().size(); i++){
	        	if(node.getValue().getConnectionPath().get(i) == iSwitch){	
	        		connectionCount++;
	        	}
	        }
		}
		
        if(node.getLeftNode() != null)  
            this.search5LDT(node.getLeftNode(), iSwitch, startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search5LDT(node.getRightNode(), iSwitch, startTime, endTime);
		return connectionCount;  
    }

	/**
	 * Tests search 6 from the assignment specification.
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of details of all calls made between start and end time.
	 */
	@Override
	public List<CallRecord> callsMade(LocalDateTime startTime, LocalDateTime endTime) {
		cRecord = new ArrayList<CallRecord>();
		
		List<CallRecord> record = search6(tree.getRoot(), startTime, endTime);
		return record;
	}
	
	/**
	 * Iterate binary tree to get a list of CallRecord
	 * 
	 * @param node The root node of binary tree
	 * @param startTime Start of time period.
	 * @param endTime End of time period.
	 * @return List of details of all calls made between start and end time.
	 */
	private List<CallRecord> search6(TreeNode<CallRecord> node, LocalDateTime startTime, LocalDateTime endTime){  
		if(node.getValue().getTimeStamp().compareTo(startTime) >= 0 
        		&& node.getValue().getTimeStamp().compareTo(endTime) <= 0){
			cRecord.add(node.getValue());
		}
		
        if(node.getLeftNode() != null)  
            this.search6(node.getLeftNode(), startTime, endTime);  
        if(node.getRightNode() != null)  
            this.search6(node.getRightNode(), startTime, endTime);
		return cRecord;  
    }
	
	
	/**
	 * private method that used to generate random number
	 * 
	 * @return random integer between 0 to 10000000
	 */
	private static int randomNum(){
		double number = Math.random();
		int i = (int) (number * 10000000);
		return i;
	}
	
	/**
	 * private method that used to determine whether input is in 5 digi format
	 * 
	 * @param str The input String
	 * @return the result
	 */
	private static boolean isNumericFive(String str) {
		if (str != null && !"".equals(str.trim())) {
	          return str.matches("^[0-9]{5}$");
	    }
	    return false;
	}
	
	/**
	 * private method that used to determine whether input is in 10 digi format
	 * 
	 * @param str The input String
	 * @return the result
	 */
	private static boolean isNumericTen(String str) {
	    if (str != null && !"".equals(str.trim())) {
	          return str.matches("^[0-9]{10}$");
	    }
	    return false;
	}
	
	/**
	 * private method that used to determine whether input is in valid date format
	 * 
	 * @param str The input String
	 * @return the result
	 */
	private static boolean isValidDate(String str) {
		if (str != null && !"".equals(str.trim())) {
	          return str.matches("((19|20|21|22)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"
				+ "([T])([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].[0-9][0-9][0-9]");
	    }
	    return false;
	}
	
	/**
	 * private method that used to check whether input Integer 
	 * is inside switches.txt file
	 * 
	 * @param node The root node of binary tree
	 * @param i The input Integer
	 * @return the result
	 */
	private boolean isInSwitchFile(TreeNode<Integer> node, int i){
		
		if(node.getValue() == i){
			flag = true;
		}
		if(node.getLeftNode() != null)  
            this.isInSwitchFile(node.getLeftNode(), i);  
        if(node.getRightNode() != null)  
            this.isInSwitchFile(node.getRightNode(), i);

        return flag;
	}
	
	/**
	 * private method that used to check if there is any
	 * duplicate redundant switch in the list
	 * @param ls The list
	 * @return the result
	 */
	private boolean findDuplicateElement(List<Integer> ls){
		if (ls == null || ls.size() == 0){
			return false;
		}
		
		for (int i = 0; i < ls.size() - 1; i++){
			if (ls.get(i) == ls.get(i+1) || ls.get(i).equals(ls.get(i+1))){
				return true;
			}
		}	
		return false;
	}
		
	
	
	
	/**
	 * The main method
	 */
	public static void main(String[] args) throws IOException {
		AutoTester test = new AutoTester();
		

	}
}