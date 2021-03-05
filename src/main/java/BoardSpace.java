public abstract class BoardSpace {

    Enums.BoardSpaceTypes type;
    final int id;

    public BoardSpace(int myID) {
        this.id = myID;
    }

    public void debugSpace() {
        System.out.println("ID: " + this.id + " type: " + this.type);
    }

    public int getID() { return this.id; }
    public Enums.BoardSpaceTypes getType() { return this.type; }

}
