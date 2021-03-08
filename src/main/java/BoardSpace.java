public abstract class BoardSpace {

    Enums.BoardSpaceTypes type;
    final int id;

    public BoardSpace(int myID, Enums.BoardSpaceTypes myType) {
        this.id = myID;
        this.type = myType;
    }

    public void debugSpace() {
        System.out.println("ID: " + this.id + " type: " + this.type);
    }

}
