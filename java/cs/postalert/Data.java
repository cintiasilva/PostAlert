package cs.postalert;

public class Data {
    int id;
    //    String time;
//    String date;
    String message;

    public Data(int id, String message) {
        this.id = id;
//       this.time = time;
//       this.date = date;
        this.message = message;
    }
    public Data( String message ) {
//       this.time = time;
        //     this.date = date;
        this.message = message;
    }

    public Data() {
    }

    public int getId() {
        return id;
    }
    //Get methods
//    public String getTime() {
//       return time;
//   }
//       public String getDate() {
//        return date;
//   }

    public String getMessage() {
        return message;
    }
    //Set methods
    public void setId(int id) {
        this.id = id;
    }

//   public void setTime(String time) {
//      this.time = time;
    //   }

//   public void setDate(String date) {
//        this.date = date;
//    }

    public void setMessage(String message) {
        this.message = message;
    }
}