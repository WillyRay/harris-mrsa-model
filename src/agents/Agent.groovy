package agents

class Agent {
    
    def static int nextAgentId = 0
    def int agentId
    
    Agent() {
	agentId = nextAgentId++
    }
    
    public int getAgentId() {
	return agentId
    }
    
}
