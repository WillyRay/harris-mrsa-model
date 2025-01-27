package agents

import java.util.HashMap

class Agent {
    
    def static int nextAgentId = 0
    def int agentId
    def HashMap attributes;
    
    Agent() {
	agentId = nextAgentId++
	attributes = new HashMap<String, Object>()
    }
    
    public int getAgentId() {
	return agentId
    }
    
    public void setAttribute(String s, Object v) {
	attributes.put(s, v);
    }
    
    public Object getAttribute(String s) {
	return attributes.get(s);
    }
    
}
