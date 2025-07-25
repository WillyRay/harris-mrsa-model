package agents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Agent {
    
    private static int nextAgentId = 0;
    private int agentId;
    private HashMap attributes;
    
    protected Agent() {
	agentId = nextAgentId++;
	attributes = new HashMap<String, Object>();
    }
    
    public int getAgentId() {
	return agentId;
    }
    
    public void setAttribute(String s, Object v) {
	attributes.put(s, v);
    }
    
    public Object getAttribute(String s) {
	return attributes.get(s);
    }
    
    public String printAttributes() {
	StringBuffer sb = new StringBuffer();
	Iterator it = attributes.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry pair = (Map.Entry)it.next(); 
	    sb.append(pair.getKey() + ": " + pair.getValue().toString() + ",");
	}
	
	return sb.toString();
    }
    
}
