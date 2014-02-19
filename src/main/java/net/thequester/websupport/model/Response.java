package net.thequester.websupport.model;

/**
 * Created by Tomo.
 */
public class Response {

    private int bla;
    private String reason;

    public Response() {}

    public Response(int bla, String reason) {
        this.bla = bla;
        this.reason = reason;
    }

    public int getBla() {
        return bla;
    }

    public void setBla(int bla) {
        this.bla = bla;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
