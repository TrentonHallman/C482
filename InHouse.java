package school.schoolproject.model;

    /** Extension of the part class that include InHouse data */
    public class InHouse extends Part {
        private int MachineID;
        public InHouse(int id, String name, double price, int stock, int min, int max, int MachineID) {
            super(id, name, price, stock, min, max);
            this.MachineID = MachineID;
        }
        /** Machine ID setter */
        public void setMachineID(int MachineID) {
            this.MachineID = MachineID;
        }
        /** Machine ID getter */
        public int getMachineID(){
            return MachineID;
        }
    }