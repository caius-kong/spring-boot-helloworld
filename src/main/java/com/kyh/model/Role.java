package com.kyh.model;

import java.io.Serializable;

/**
 * Created by kongyunhui on 2017/4/25.
 */
public class Role implements Serializable{
    private Long id;
    private String name;
    private String description;
    private int locked;
    private String resourceIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", locked=" + locked +
                ", resourceIds='" + resourceIds + '\'' +
                '}';
    }
}
