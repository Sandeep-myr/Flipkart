package com.rt.pot.mailTemplate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rt.pot.model.Address;
import com.rt.pot.model.Admin;
import com.rt.pot.model.Contacts;

import lombok.Data;

@Component
@Data
public class MailTemplate {

	//==================================================TEMPLATE FOR WHO'S CANDIDATE ARE REGISTER IN OUR WEBSITE ====================
	public String registrationTemplate(Admin admin) {
		List<Contacts> contactList = admin.getAdminContacts().stream().collect(Collectors.toList());
		List<Address> addressList = admin.getAdminAddresses().stream().collect(Collectors.toList());
		String primaryAddress = addressList.get(0).getStreet() + " , " + addressList.get(0).getCity() + " , "
				+ addressList.get(0).getState() + " , " + addressList.get(0).getCountry() + " , "
				+ addressList.get(0).getZipCode();
		String secondaryAddress = addressList.get(1).getStreet() + " , " + addressList.get(1).getCity() + " , "
				+ addressList.get(1).getState() + " , " + addressList.get(1).getCountry() + " , "
				+ addressList.get(1).getZipCode();
		String registrationSuccessMailTemplate = " <strong>Dear " + admin.getAdminName()
				+ " Your Request Has Been Accept Our Managment Team Will Contact WIth You very Soon . Thanks For Your Intrest In Our Orgnization . <strong><br>  <table style='border-collapse: collapse; width: 50%; margin: 20px auto;'>\r\n"
				+ "        <tr style='border: 2px solid #3498db;'>\r\n"
				+ "            <td style='color: #e74c3c; padding: 10px;'><strong>Candidate Name</strong></td>\r\n"
				+ "            <td style='background-color: #ecf0f1; padding: 10px;'><span>" + admin.getAdminName()
				+ "</span></td>\r\n" + "        </tr>\r\n" + "        <tr style='border: 2px solid #3498db;'>\r\n"
				+ "            <td style='color: #e74c3c; padding: 10px;'><strong>primary Mobile Number</strong></td>\r\n"
				+ "            <td style='background-color: #ecf0f1; padding: 10px;'><span>"
				+ contactList.get(1).getMobileNumber() + "</span></td>\r\n" + "        </tr>\r\n"
				+ "        <tr style='border: 2px solid #3498db;'>\r\n"
				+ "            <td style='color: #e74c3c; padding: 10px;'><strong>primary E-Mail</strong></td>\r\n"
				+ "            <td style='background-color: #ecf0f1; padding: 10px;'><span>"
				+ contactList.get(1).getEmailId() + "</span></td>\r\n" + "        </tr>\r\n"
				+ "        <tr style='border: 2px solid #3498db;'>\r\n"
				+ "            <td style='color: #e74c3c; padding: 10px;'><strong>Temporary Address</strong></td>\r\n"
				+ "            <td style='background-color: #ecf0f1; padding: 10px;'><span>" + primaryAddress
				+ "</span></td>\r\n" + "        </tr>\r\n" + "        <tr style='border: 2px solid #3498db;'>\r\n"
				+ "            <td style='color: #e74c3c; padding: 10px;'><strong>Permanent Address</strong></td>\r\n"
				+ "            <td style='background-color: #ecf0f1; padding: 10px;'><span>" + secondaryAddress
				+ "</span></td>\r\n" + "        </tr>\r\n" + "    </table>";
		return registrationSuccessMailTemplate;
	}
	
	
	//=======================================TEMPLATE FOR WHO'S CANDIDATE APPROVED BY MANAGER SEND THE CONGRATULATION MALE WITH USERNAME AND PASSWORD==================
	
	public String approvalOfAdminTemplate(String name, String emailId, String password) {
		
		String approvalTemplate="<Strong >Congratulations!!!</Strong><br><I>Dear "+name+" Your Request Has Been Approved By Our Managment Now You Are The Admin In our Website Flipkart You Login Details Given Below </I>"
				+ "<br><strong> UserName: </strong>"+emailId+" <br><strong> Password: </strong>"+password+"<br><img src='https://pbs.twimg.com/media/FHgp_dEVcAAlf0n.jpg'>";
		return approvalTemplate;
	}
	
	
	
	
	//=========================================TEMPLATE WHO'S CANDIDATE ARE REJECTED BY MANAGER SEND THE REGRETION MAIL TO THAT CANDIDATE======================
	
	public String rejectionOfAdminTemplate(String name) {
		String rejectionTemplate="<strong>Dear "+name+"We regret to inform you that your application for the admin role has been rejected.Thank you for your interest and time.</strong><br><img src='https://thumbs.dreamstime.com/z/trustus-261899021.jpg'><br>Sincerely,The Hiring Team";
		return rejectionTemplate;
	}
	
	
	
	//===========================================WHEN ACCOUNT HAS BEEN TERMINATED PERMANENTLY THEN SEND THE MAIL IT IS TEMPLATE FOR SENDING THE ATTRACTIVE MAIL 
	
	
	public String accountDeleteOfAdminTemplate(String name) {
		
		String accountDeleteTemplate = "<html><body style='font-family: \"Arial\", sans-serif; background-color: #f5f5f5; color: #333; padding: 20px;'>\r\n"
				+ "\r\n"
				+ "  <div style='max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>\r\n"
				+ "    <h2 style='color: #ff5722; text-align: center;'>Account Deletion Notification</h2>\r\n"
				+ "\r\n"
				+ "    <p style='font-size: 16px; line-height: 1.6; margin-bottom: 20px;'>\r\n"
				+ "      Dear Admin,\r\n"
				+ "    </p>\r\n"
				+ "\r\n"
				+ "    <p style='font-size: 16px; line-height: 1.6; margin-bottom: 20px;'>\r\n"
				+ "     Hii "+name+" This is to inform you that the account associated with your administration role has been deleted. If you did not initiate this action or have any concerns, please contact our support team immediately.\r\n"
				+ "    </p>\r\n"
				+ "\r\n"
				+ "    <img src='https://images.tv9hindi.com/wp-content/uploads/2023/10/flipkart-file-photo.jpg' alt='Company Logo' style='max-width: 100%; height: auto; margin-bottom: 20px; border-radius: 5px;'>\r\n"
				+ "\r\n"
				+ "    <p style='font-size: 16px; line-height: 1.6; margin-bottom: 20px;'>\r\n"
				+ "      Thank you for your understanding.\r\n"
				+ "    </p>\r\n"
				+ "\r\n"
				+ "    <p style='font-size: 16px; line-height: 1.6; margin-bottom: 20px; color: #777;'>\r\n"
				+ "      Best regards,<br>\r\n"
				+ "      Your Company Name\r\n"
				+ "    </p>\r\n"
				+ "  </div>\r\n"
				+ "\r\n"
				+ "</body>\r\n"
				+ "</html>";
		
		return accountDeleteTemplate;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
