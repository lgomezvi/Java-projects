// Laura Gomez
// CS1027
// This class helps the frog get to its partner by taking the best path
public class FrogPath {
    private Pond pond;
    // initializes a pond to use 
    public FrogPath(String filename) {
        try {
            pond = new Pond(filename);
        } catch (Exception e) {
            return;
        }
    }

    // helper that calculates the priorities of the different cells 
    private double calculatePriority(Hexagon neighbor, Hexagon currCell) {
    	
        double priority = 6.0;

        // 3.0 End (Franny)
        if (neighbor.isEnd()) {
            priority = 3.0;
            // 4.0 Lilypad
        } else if (neighbor.isLilyPadCell()) {
            priority = 4.0;
            // 5.0 Reeds
        } else if (neighbor.isReedsCell()) {
            priority = 5.0;
            // FoodHexagon Checks
            // 0.0 3 flies
            // 1.0 2 flies
            // 2.0 1 fly
        } else if (neighbor instanceof FoodHexagon) {
            // cast neighbor to FoodHexagon to use getNumFlies
            int numFlies = ((FoodHexagon) neighbor).getNumFlies();
            priority = 3 - numFlies; // calculating the priority for flies 
            // avoid this all at costs
        } else if (neighbor.isMudCell() || neighbor.isAlligator() ){
            priority = 10.0; // high priority to avoid this cells (allergic to mud, must never jump to an alligator)
        }

        // 10.0 alligator (check all surrounding)
        for (int i = 0; i < 6; i++) {
                Hexagon adjacent = neighbor.getNeighbour(i);
                if (adjacent != null && adjacent.isAlligator()) {
                    priority = 10.0;
                    ////System.out.println("alligator " + neighbor.getID() + "->" + adjacent.getID());
                    break;
                
                	}
        		}

        // ignore these they are just to debug 
       // if (currCell.isLilyPadCell()) {
            //System.out.println("[isLilyPadCell] " + currCell.getID() + "->" + neighbor.getID() + " P:" + priority);
       // } else {
            //System.out.println("Check Pair " + currCell.getID() + "->" + neighbor.getID() + " P:" + priority);
       // }

        return priority;
    }
    
    // based of priorites the best cell to go is returned 
    public Hexagon findBest(Hexagon currCell) {
    	// keeps track of the possible cells to jump to 
        ArrayUniquePriorityQueue<Hexagon> unmarked = new ArrayUniquePriorityQueue<Hexagon>();

        for (int i = 0; i < 6; i++) {
            Hexagon neighbor;

            neighbor = currCell.getNeighbour(i); // searches in a circle clockwise
            if (neighbor != null && !neighbor.isMarked() ) {
                double priority = calculatePriority(neighbor, currCell); // calculates priority
                if(priority < 10.0){
                    unmarked.add(neighbor, priority); // adding possible cells to jump to 
                }
            }
        }

        if (currCell.isLilyPadCell()) { // since if lillypad must check up to two cells away 
            for (int i = 0; i < 6; i++) {
                Hexagon neighbor;
                neighbor = currCell.getNeighbour(i); // checks surrounding 6 
                for (int j = 0; j < 6; j++) {
                    if (neighbor != null) {
                        Hexagon secondNeighbor = neighbor.getNeighbour(j); // for every adjacent cell we calculate its adjacent cells as well if we haven't already
                        if (secondNeighbor != null && !secondNeighbor.isMarked()) {
                            
                            // +0.5 Cell 2 away in a straight line
                            // +1.0 Cell 2 away not in a straight line
                            double additionalPriority = 1.0;
                            
                            if(i == j){ // straight line 
                                additionalPriority = 0.5;
                            } 
                            
                            double priority = calculatePriority(secondNeighbor, currCell) + additionalPriority;
                            //System.out.println("updated priority: " + priority);

                            if(priority < 10.0){
                                unmarked.add(secondNeighbor, priority);
                            }
                        }
                    }
                }

            }
        }
        // returns best cell to jump to
        if (!unmarked.isEmpty()) {
            return unmarked.removeMin();
        } else {
            return null;
        }

    }
    
    // string representation of the path taken 
    public String findPath() {
        Hexagon curr = pond.getStart();
        ArrayStack<Hexagon> visited = new ArrayStack<>(); // stack to keep track of movements made 
        Hexagon next;

        visited.push(curr);

        curr.markInStack();
        int fliesEaten = 0;
        String ids = "";

        // adds ids to the string 
        while (!visited.isEmpty()) {
            curr = visited.peek();
            ids += curr.getID() + " ";

            if (curr.isEnd()) {
                break;
            }

            // keeps track of flies eaten 
            if (curr instanceof FoodHexagon) {
                fliesEaten += ((FoodHexagon) curr).getNumFlies();
                ((FoodHexagon) curr).removeFlies();
            }

            next = findBest(curr);
            // to backtrack 
            if (next == null) {
                visited.pop();
                curr.markOutStack();
            } else {
            	// adding visited cells
                visited.push(next);
                next.markInStack();
            }

        }
        if (visited.isEmpty()) {
            return "No solution";

        } else {
            return ids + "ate " + fliesEaten + " flies";
        }

    }

    // to test 
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No map file specified in the arguments");
            return;
        }
        FrogPath fp = new FrogPath(args[0]);
        Hexagon.TIME_DELAY = 500; // Change this time delay as desired.
        String result = fp.findPath();
        System.out.println(result);
    }

}
// the end :)