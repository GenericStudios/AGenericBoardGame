public abstract class BoardSpace {

    private Enums.BoardSpaceTypes type;
    private final int id;

    public BoardSpace(int myID, Enums.BoardSpaceTypes myType) {
        this.id = myID;
        this.type = myType;
    }

    public void debugSpace() {
        System.out.println("ID: " + this.id + " type: " + this.type);
    } // TODO: remove when no longer needed

    public int getID() {
        return this.id;
    }

    public Enums.BoardSpaceTypes getType() {
        return this.type;
    }

}
