
package com.example.edistynytmobiiliohjelmointi.datatypes.weatherstation;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class _1 {

    @SerializedName("v")
    @Expose
    private float v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public _1() {
    }

    /**
     * 
     * @param v
     */
    public _1(float v) {
        super();
        this.v = v;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public _1 withV(float v) {
        this.v = v;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("v");
        sb.append('=');
        sb.append(this.v);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
