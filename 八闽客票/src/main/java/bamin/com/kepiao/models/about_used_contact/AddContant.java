package bamin.com.kepiao.models.about_used_contact;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjb on 2016/3/11.
 */
public class AddContant implements Serializable{
//    private int position;
    private List<UsedContactInfo> theAddContact;

    public AddContant(List<UsedContactInfo> theAddContact) {
        this.theAddContact = theAddContact;
    }

    public List<UsedContactInfo> getTheAddContact() {
        return theAddContact;
    }

    public void setTheAddContact(List<UsedContactInfo> theAddContact) {
        this.theAddContact = theAddContact;
    }
}
