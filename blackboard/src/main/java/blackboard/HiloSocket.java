
package blackboard;

import entidades.BlackBoardObject;

public class HiloSocket extends Thread{
    private BlackBoardObject bbo;

    public HiloSocket(BlackBoardObject bbo) {
        this.bbo = bbo;
    }

    public HiloSocket() {
    }

    @Override
    public void run() {
        Blackboard bb = Blackboard.getInstance();
        bb.addProblem(bbo);
        this.interrupt();
    }
    
    
}
