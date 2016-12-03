package com.example.utilisateur.cookhelper;

/**
 * Created by ced on 2016-11-30.
 */

public class Ingredient {


        //------------------------
        // MEMBER VARIABLES
        //------------------------

        //Ingredient Attributes
        private String name;

        //------------------------
        // CONSTRUCTOR
        //------------------------

        public Ingredient(String aName)
        {
            name = aName;
        }

        //------------------------
        // INTERFACE
        //------------------------

        public boolean setName(String aName)
        {
            boolean wasSet = false;
            name = aName;
            wasSet = true;
            return wasSet;
        }

        public String getName()
        {
            return name;
        }

        public void delete()
        {}


        public String toString()
        {
            String outputString = "";
            return super.toString() + "["+
                    "name" + ":" + getName()+ "]"
                    + outputString;
        }
    }

