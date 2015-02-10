package org.mythtv.android.player;

/**
 * Created by dmfrey on 2/7/15.
 */
public class NavigationItem {

    private int iconId;
    private String title;

    public NavigationItem() { }

    public NavigationItem( int iconId, String title ) {

        this.iconId = iconId;
        this.title = title;

    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId( int iconId ) {

        this.iconId = iconId;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {

        this.title = title;

    }

}
