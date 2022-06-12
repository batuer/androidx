package com.gusi.androidx.module.net;

import java.io.Serializable;

/**
 * @author Ylw
 * @since 2022/6/12 9:30
 */
public class HotKey implements Serializable {
  //  "id": 6,
//      "link": "",
//      "name": "面试",
//      "order": 1,
//      "visible": 1
  private int id;
  private int order;
  private int visible;
  private String link;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getVisible() {
    return visible;
  }

  public void setVisible(int visible) {
    this.visible = visible;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "HotKey{" +
        "id=" + id +
        ", order=" + order +
        ", visible=" + visible +
        ", link='" + link + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
