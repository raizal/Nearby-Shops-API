package org.nearbyshops;

import org.nearbyshops.DAOsPreparedRoles.*;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.ModelErrorMessages.ErrorNBSAPI;
import org.nearbyshops.ModelRoles.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by sumeet on 9/9/16.
 */


@Provider
public class AuthenticationFilter implements ContainerRequestFilter {


    private AdminDAOPrepared adminDAOPrepared = Globals.adminDAOPrepared;
    private StaffDAOPrepared staffDAOPrepared = Globals.staffDAOPrepared;
//    private DistributorDAOPrepared distributorDAOPrepared = Globals.distributorDAOPrepared;
//    private DistributorStaffDAOPrepared distributorStaffDAO = Globals.distributorStaffDAOPrepared;
    private DeliveryGuySelfDAO deliveryGuySelfDAO = Globals.deliveryGuySelfDAO;
    private EndUserDAONew endUserDAOPrepared = Globals.endUserDAONew;
    private ShopAdminDAO shopAdminDAO = Globals.shopAdminDAO;
    private ShopStaffDAOPrepared shopStaffDAO = Globals.shopStaffDAOPrepared;


    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked for all users !!").build();


    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

//        System.out.println("Security Fileter");

            if (method.isAnnotationPresent(DenyAll.class)) {

                throw new ForbiddenException("Access is ErrorNBSAPI !");
            }



        //Verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);

                throw new NotAuthorizedException("Access is Denied ! Credentials not present");
            }


            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");



            //Decode username and password
            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));


            System.out.println("Username:Password" + usernameAndPassword);

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
            System.out.println(username);
            System.out.println(password);

            Globals.accountApproved = isUserAllowed(username, password, rolesSet);


            }
        }


    private Object isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {


        //        boolean isAllowed = false;

        //        boolean isEnabled = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);


        for(String role : rolesSet)
        {

            if(role.equals(GlobalConstants.ROLE_ADMIN))
            {

                Admin admin = adminDAOPrepared.checkAdmin(username,password);

                if(admin != null)
                {
                    return admin;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_STAFF_DISABLED))
            {
                Staff staff = staffDAOPrepared.checkStaff(username,password);

                if(staff!=null )
                {
                    return staff;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_STAFF))
            {
                Staff staff = staffDAOPrepared.checkStaff(username,password);

                if(staff!=null)
                {
                    if(staff.getEnabled())
                    {
                        return staff;
                    }
                    else
                    {

                        Response response = Response.status(403)
                                .entity(new ErrorNBSAPI(403, "Your account is disabled. Contact administrator to know more !"))
                                .build();

                        throw new ForbiddenException("Username or password is Incorrect !",response);

                    }
                }


            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_STAFF_DISABLED))
            {
                ShopStaff shopStaff = shopStaffDAO.checkShopStaff(username,password);

                if(shopStaff!=null )
                {
                    return shopStaff;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_STAFF))
            {
                ShopStaff shopStaff = shopStaffDAO.checkShopStaff(username,password);

                if(shopStaff!=null)
                {
                    if(shopStaff.getEnabled())
                    {
                        return shopStaff;
                    }
                    else
                    {

                        Response response = Response.status(403)
                                .entity(new ErrorNBSAPI(403, "Your account is disabled. Contact administrator to know more !"))
                                .build();

                        throw new ForbiddenException("Username or password is Incorrect !",response);

                    }
                }
            }
            else if(role.equals(GlobalConstants.ROLE_END_USER))
            {
                EndUser endUser = endUserDAOPrepared.checkEndUser(username,password);
                // Distributor account exist and is enabled
                if(endUser!=null )
                {
                    return endUser;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_DELIVERY_GUY_SELF))
            {
                DeliveryGuySelf deliveryGuySelf = deliveryGuySelfDAO.checkDeliveryGuy(username,password);
                // Distributor account exist and is enabled
                if(deliveryGuySelf!=null )
                {
                    if(!deliveryGuySelf.getEnabled())
                    {
                        Response response = Response.status(403)
                                .entity(new ErrorNBSAPI(403, "Your account is disabled. Contact administrator to know more !"))
                                .build();

                        throw new ForbiddenException("Permission denied !",response);
                    }
                    else
                    {
                        return deliveryGuySelf;
                    }

                }
            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_ADMIN_DISABLED))
            {
                ShopAdmin shopAdmin = shopAdminDAO.checkShopAdmin(username,password);
                // Distributor account exist and is enabled
                if(shopAdmin!=null )
                {
                    return shopAdmin;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_SHOP_ADMIN))
            {
                ShopAdmin shopAdmin = shopAdminDAO.checkShopAdmin(username,password);
                // Distributor account exist and is enabled

                System.out.println("Identifying shop Admin ...");

                if(shopAdmin!=null )
                {
                    if(!shopAdmin.getEnabled())
                    {
                        Response response = Response.status(403)
                                .entity(new ErrorNBSAPI(403, "Your account is disabled. Contact administrator to know more !"))
                                .build();

                        System.out.println("Shop Admin disapproved permission denied ...");

                        throw new ForbiddenException("Permission denied !",response);
                    }
                    else
                    {
                        System.out.println("Shop Admin successfully approved ...");
                        return shopAdmin;
                    }

                }

                System.out.println("Shop Admin null ...");

            }
        }


        throw new NotAuthorizedException("Access is Denied ! We are not able to Identify you. ");
    }

}






/*Configuring Security Context*/


/*
else
        {
        requestContext.setSecurityContext(new SecurityContext() {
@Override
public Principal getUserPrincipal() {
        return new Principal() {
@Override
public String getName() {
        return username;
        }
        };
        }

@Override
public boolean isUserInRole(String s) {


        return false;
        }

@Override
public boolean isSecure() {
        return false;
        }

@Override
public String getAuthenticationScheme() {
        return null;
        }
        });

        }*/
