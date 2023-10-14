public enum Campus {
    NEW_BRUNSWICK(0), NEWARK(1), CAMDEN(2);

    private final int abbreviation;                     // Abbreviation for campuses (0,1,2)

     Campus(int campusCode){                            // Binds abbreviation to each campus
        abbreviation = campusCode;
    }

    public static Campus getCampus(int value) {         // Returns campus based on input value (Ex: 1->Newark)
        for (Campus campus : Campus.values()) {
            if (campus.abbreviation == value) {
                return campus;
            }
        }
        return null;                                    // throws an exception if the value isn't recognized
    }


}
