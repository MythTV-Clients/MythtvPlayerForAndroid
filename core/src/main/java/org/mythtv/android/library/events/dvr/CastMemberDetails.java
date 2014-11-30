package org.mythtv.android.library.events.dvr;

/**
 * Created by dmfrey on 11/12/14.
 */
public class CastMemberDetails {

    private String name;
    private String characterName;
    private String role;
    private String translatedRole;

    public CastMemberDetails() { }

    public CastMemberDetails(String name, String characterName, String role, String translatedRole) {
        this.name = name;
        this.characterName = characterName;
        this.role = role;
        this.translatedRole = translatedRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTranslatedRole() {
        return translatedRole;
    }

    public void setTranslatedRole(String translatedRole) {
        this.translatedRole = translatedRole;
    }
}
