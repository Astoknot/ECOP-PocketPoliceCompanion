package com.bellatrix.aditi.e_cop.Database;

/**
 * Created by Aditi on 07-04-2018.
 */
//to make the system online the same contract can be hosted
import android.provider.BaseColumns;

public final class Contract{
    private Contract(){}

    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_FIRST_NAME = "FirstName";
        public static final String COLUMN_LAST_NAME = "LastName";
        public static final String COLUMN_CONTACT_NUMBER = "ContactNumber";
        public static final String COLUMN_ADHAAR_NUMBER = "AdhaarNumber";
        public static final String COLUMN_EMAIL = "eMail";
    }

    public static class CopEntry implements BaseColumns{
        public static final String TABLE_NAME = "Cop";
        public static final String COLUMN_COP_ID = "CopID";
        public static final String COLUMN_STATION_POSTED = "StationPosted";
        public static final String COLUMN_FIRST_NAME = "FirstName";
        public static final String COLUMN_LAST_NAME = "LastName";
        public static final String COLUMN_RANK = "Rank";
        public static final String COLUMN_POST = "Post";
    }

    public static class CourtEntry implements BaseColumns{
        public static final String TABLE_NAME = "Court";
        public static final String COLUMN_COURT_ID = "CourtID";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_CITY = "City";
    }

    public static class CredentialsEntry implements BaseColumns{
        public static final String TABLE_NAME = "LoginCredentials";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_PASSWORD = "Password";
    }

    public static class ComplainEntry implements BaseColumns {
        public static final String TABLE_NAME = "Complain";
        public static final String COLUMN_COMPLAIN_NUMBER = "ComplainNumber";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_USER = "User";
        public static final String COLUMN_VICTIM = "Victim";
        public static final String COLUMN_FILED_DATE = "FiledDate";
    }

    public static class FIREntry implements BaseColumns {
        public static final String TABLE_NAME = "FIR";
        public static final String COLUMN_FIR_NUMBER = "FIRNumber";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_USER = "User";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_VICTIM = "Victim";
        public static final String COLUMN_ACCUSED = "Accused";
        public static final String COLUMN_LODGED_DATE = "LodgedDate";
    }

    public static class CasesEntry implements BaseColumns {
        public static final String TABLE_NAME = "Cases";
        public static final String COLUMN_CASE_NUMBER = "CaseNumber";
        public static final String COLUMN_COURT_ID = "CourtID";
        public static final String COLUMN_FIR_NUMBER = "FIRNumber";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_LAWYER = "Lawyer";
        public static final String COLUMN_PROSECUTOR = "ProsecutorAssigned";
    }

    public static class UpdatesEntry implements BaseColumns {
        public static final String TABLE_NAME = "Updates";
        public static final String COLUMN_UPDATE_NUMBER = "UpdateNumber";
        public static final String COLUMN_CASE_NUMBER = "CaseNumber";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_DESCRIPTION = "Description";
    }
}
