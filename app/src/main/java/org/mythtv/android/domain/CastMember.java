/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.domain;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 11/12/14.
 */
public class CastMember {

    private String name;
    private String characterName;
    private String role;
    private String translatedRole;

    public CastMember() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public CastMember(String name, String characterName, String role, String translatedRole) {
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

    @Override
    public String toString() {
        return "CastMember{" +
                "name='" + name + '\'' +
                ", characterName='" + characterName + '\'' +
                ", role='" + role + '\'' +
                ", translatedRole='" + translatedRole + '\'' +
                '}';
    }

}
