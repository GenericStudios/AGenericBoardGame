public abstract class BoardSpace {

    private Enums.BoardSpaceTypes type;
    private final int id;

    public BoardSpace(int myID, Enums.BoardSpaceTypes myType) {
        this.id = myID;
        this.type = myType;
    }

    public int getID() {
        return this.id;
    }

    public Enums.BoardSpaceTypes getType() {
        return this.type;
    }

}
