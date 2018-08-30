package com.jsh.model.po;

@SuppressWarnings("serial")
public class Role implements java.io.Serializable {
    private Long Id;
    private String Name;
    private String isystem;

    public Role() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    /**
     * Getter method for property <tt>isystem</tt>.
     *
     * @return property value of isystem
     */
    public String getIsystem() {
        return isystem;
    }

    /**
     * Setter method for property <tt>isystem</tt>.
     *
     * @param isystem value to be assigned to property isystem
     */
    public void setIsystem(String isystem) {
        this.isystem = isystem;
    }
}