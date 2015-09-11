package com.phoenixmarketcity.android.phoenix.webservices;

import android.content.Context;

import com.phoenixmarketcity.android.phoenix.PhoenixApplication;
import com.phoenixmarketcity.android.phoenix.activities.BrandsActivity;
import com.phoenixmarketcity.android.phoenix.activities.CollectionsActivity;
import com.phoenixmarketcity.android.phoenix.activities.CommentsActivity;
import com.phoenixmarketcity.android.phoenix.activities.ContestListActivity;
import com.phoenixmarketcity.android.phoenix.activities.EventCoverActivity;
import com.phoenixmarketcity.android.phoenix.activities.EventDetailsActivity;
import com.phoenixmarketcity.android.phoenix.activities.FilterPageActivity;
import com.phoenixmarketcity.android.phoenix.activities.MovieDetailsActivity;
import com.phoenixmarketcity.android.phoenix.activities.SearchResultActivity;
import com.phoenixmarketcity.android.phoenix.cards.BaseCardView;
import com.phoenixmarketcity.android.phoenix.cards.CardEventTicketView;
import com.phoenixmarketcity.android.phoenix.cards.CardParkingView;
import com.phoenixmarketcity.android.phoenix.cards.CardStoreDetailsView;
import com.phoenixmarketcity.android.phoenix.cards.CardUtils;
import com.phoenixmarketcity.android.phoenix.util.ConfigManager;
import com.phoenixmarketcity.android.phoenix.util.MenuAPI;
import com.phoenixmarketcity.android.phoenix.util.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


/**
 * Created by neeraj.nayan on 13/06/15.
 */
public class WebService {
    private static final String STUB_SERVER_BASE_URL = "https://s3-ap-southeast-1.amazonaws.com/lumbinielite";
    private static final String OUTLET_HOME_PAGE_ID = "outlet_home";

    public static final String TYPE_OF_OUTLET_ALL_STORE = "all_store";
    public static final String TYPE_OF_OUTLET_POPULAR_STORE = "popular_store";
    public static final String TYPE_OF_OUTLET_FEATURED_STORE = "featured_store";

    private final PageLayoutWebService mLumbiniAWSAdapter;
    private final PageAPIWebService mWebServiceAdapter;
    private final UserAPIWebService mUserWebServiceAdapater;
    private Context mContext;

    private static class Holder {
        static final WebService INSTANCE = new WebService(PhoenixApplication.getInstance());
    }

    // Returns singleton instance
    public static WebService getInstance() {
        return Holder.INSTANCE;
    }

    // Constructor
    private WebService(Context context) {
        mContext = context;

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "Application/json");
            }
        };

        RestAdapter lumbiniAWSAdapter = new RestAdapter.Builder()
                .setEndpoint(STUB_SERVER_BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.NONE).build();

        mLumbiniAWSAdapter = lumbiniAWSAdapter.create(PageLayoutWebService.class);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
                ConfigManager.getInstance().getBaseUrl())
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL).build();

        mWebServiceAdapter = restAdapter.create(PageAPIWebService.class);

        RestAdapter userServiceRestAdapater = new RestAdapter.Builder().setEndpoint(
                ConfigManager.getInstance().getBaseUserApiUrl())
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL).build();

        mUserWebServiceAdapater = userServiceRestAdapater.create(UserAPIWebService.class);
    }

    /************************ Public Methods ****************************/

    public void getPage(final String pageName, Callback<List<CardUtils.CardConfiguration>> cb) {
        mLumbiniAWSAdapter.getPage(pageName, cb);
    }

    public void get1x1Card(final String cardNumber, Callback<BaseCardView.CardViewData> cb) {
        mLumbiniAWSAdapter.get1x1Card(cardNumber, cb);
    }

    public void get1x2Card(final String cardNumber, Callback<BaseCardView.CardViewData> cb) {
        mLumbiniAWSAdapter.get1x2Card(cardNumber, cb);
    }

//    public void getCarouselCard(final String cardNumber, Callback<List<CardCarouselView.CardCarouselViewData>> cb) {
//        mLumbiniAWSAdapter.getCarouselCard(cardNumber, cb);
//    }

    public void getEventTicketCard(final String cardNumber, Callback<CardEventTicketView.CardEventTicketViewData> cb) {
        mLumbiniAWSAdapter.getEventTicketCard(cardNumber, cb);
    }

    public void getParkingTicketCard(final String cardNumber, Callback<CardParkingView.CardParkingViewData> cb) {
        mLumbiniAWSAdapter.getParkingTicketCard(cardNumber, cb);
    }
    public void getStoreDetailsCard(final String cardNumber, Callback<CardStoreDetailsView.CardStoreDetailsViewData> cb) {
        mLumbiniAWSAdapter.getStoreDetailsCard(cardNumber, cb);
    }

//    public void getEventDetails(final String eventId, Callback<EventDetailsActivity.EventDescriptionData> cb) {
//        mLumbiniAWSAdapter.getEventDetails(eventId, cb);
//    }

    public void getCommentList(final String eventcomments, Callback<List<CommentsActivity.CommentData>> cb) {
        mLumbiniAWSAdapter.getCommentList(eventcomments, cb);
    }

    public void getCoverPageData(final String eventId, Callback<List<EventCoverActivity.EventCoverData>> cb) {
        mLumbiniAWSAdapter.getCoverPageData(eventId, cb);
    }
    public void getSearchPage(final String pageName, Callback<List<SearchResultActivity.SearchResultDataList>> cb) {
        mLumbiniAWSAdapter.getSearchPage(pageName, cb);
    }

    public void getContestList(final String eventId, Callback<ContestListActivity.EvenContestData> cb) {
        mLumbiniAWSAdapter.getContestList(eventId, cb);
    }

    public void getCollectionsList(final String filterId, Callback<List<CollectionsActivity.CollectionListItems>> cb) {
        mLumbiniAWSAdapter.getCollectionsList(filterId, cb);
    }

    // API Interface
    private interface PageLayoutWebService {
        @GET("/JSON/{pagename}.json")
        public void getPage(@Path("pagename") String pageName, Callback<List<CardUtils.CardConfiguration>> cb);

        @GET("/JSON/card_{cardnumber}.json")
        public void get1x1Card(@Path("cardnumber") String cardNumber, Callback<BaseCardView.CardViewData> cb);

        @GET("/JSON/card_{cardnumber}.json")
        public void get1x2Card(@Path("cardnumber") String cardNumber, Callback<BaseCardView.CardViewData> cb);

        @GET("/JSON/card_{cardnumber}.json")
        public void getEventTicketCard(@Path("cardnumber") String cardNumber, Callback<CardEventTicketView.CardEventTicketViewData> cb);

        @GET("/JSON/card_{cardnumber}.json")
        public void getParkingTicketCard(@Path("cardnumber") String cardNumber, Callback<CardParkingView.CardParkingViewData> cb);

        @GET("/JSON/card_{cardnumber}.json")
        public void getStoreDetailsCard(@Path("cardnumber") String cardNumber, Callback<CardStoreDetailsView.CardStoreDetailsViewData> cb);

//        @GET("/JSON/{cardnumber}.json")
//        public void getEventDetails(@Path("cardnumber") String eventId, Callback<EventDetailsActivity.EventDescriptionData> cb);

        @GET("/JSON/{cardnumber}.json")
        public void getCommentList(@Path("cardnumber") String evtcom, Callback<List<CommentsActivity.CommentData>> cb);

        @GET("/JSON/{cardnumber}.json")
        public void getCoverPageData(@Path("cardnumber") String eventId, Callback<List<EventCoverActivity.EventCoverData>> cb);

        @GET("/JSON/{pagename}.json")
        public void getSearchPage(@Path("pagename") String pageName, Callback<List<SearchResultActivity.SearchResultDataList>> cb);

        @GET("/JSON/{cardnumber}.json")
        public void getContestList(@Path("cardnumber") final String eventId, Callback<ContestListActivity.EvenContestData> cb);

        @GET("/JSON/{cardnumber}.json")
        public void getCollectionsList(@Path("cardnumber") final String filterId, Callback<List<CollectionsActivity.CollectionListItems>> cb);
    }

    public void getLayout(String pageName, Callback<CardUtils.CardConfiguration> cb) {
        mWebServiceAdapter.getLayout(pageName,
                Integer.toString(ConfigManager.getInstance().getDefaultMallIndex()), cb);
    }

    public void getOutletPageLayout(String outletId, Callback<CardUtils.CardConfiguration> cb) {
        mWebServiceAdapter.getLayout(OUTLET_HOME_PAGE_ID, outletId, cb);
    }

    public void getCardContents(String cardId, Callback<BaseCardView.CardViewData> cb) {
        mWebServiceAdapter.getCardContents(cardId,
//                ConfigManager.getInstance().getScreenResolution(mContext.getResources()),
                "master",
                cb);
    }

    public void getOutletList(String typeOfMall, int pageId, int limit,
                              Callback<List<BrandsActivity.BrandListItem>> cb) {
        mWebServiceAdapter.getOutletList(ConfigManager.getInstance().getDefaultMallIndex(),
                pageId, limit, typeOfMall, cb);
    }

    public void getCategories(Callback<List<FilterPageActivity.Categories>> cb) {
        mWebServiceAdapter.getCategories(cb);
    }

    public void getEventDetails(final int eventId, Callback<EventDetailsActivity.EventDescriptionData> cb) {
        mWebServiceAdapter.getEventDetails(eventId, cb);
    }

    public void getMovieDetails(final int movieName, Callback<MovieDetailsActivity.MovieDetails> cb) {
        mWebServiceAdapter.getMovieDetails(movieName, cb);
    }

    public void getMenuTypes(final int outletId, Callback<List<MenuAPI.MenuTypes>> cb) {
        mWebServiceAdapter.getMenuTypes(outletId, cb);
    }

    public void getMenuGroups(final int menuTypeId, Callback<List<MenuAPI.MenuGroup>> cb) {
        mWebServiceAdapter.getMenuGroups(menuTypeId, cb);
    }

    public void getMenuItems(final int menuItemId, Callback<List<MenuAPI.MenuItem>> cb) {
        mWebServiceAdapter.getMenuItems(menuItemId, cb);
    }

    private interface PageAPIWebService {
        @FormUrlEncoded
        @POST("/layoutApi/getLayout")
        public void getLayout(@Field("page") String page, @Field("type_id") String mallId,
                                  Callback<CardUtils.CardConfiguration> cb);

        @FormUrlEncoded
        @POST("/layoutApi/cardContents")
        public void getCardContents(@Field("cardid") String cardId, @Field("resolution") String resolution,
                              Callback<BaseCardView.CardViewData> cb);

        @FormUrlEncoded
        @POST("/outletApi/listOutletsofMall")
        public void getOutletList(@Field("mall_id") int mallId, @Field("page")int page,
                                  @Field("limit")int limit, @Field("type_of_sort") String typeOfMall,
                                  Callback<List<BrandsActivity.BrandListItem>> cb);

        @FormUrlEncoded
        @GET("/outletApi/listAllCategoriesWithContent")
        public void getCategories(Callback<List<FilterPageActivity.Categories>> cb);

        @FormUrlEncoded
        @POST("/eventApi/eventDetail")
        public void getEventDetails(@Field("event_id") int eventId, Callback<EventDetailsActivity.EventDescriptionData> cb);

        @FormUrlEncoded
        @POST("/movieApi/movieDetail")
        public void getMovieDetails(@Field("movie_id") int movieId, Callback<MovieDetailsActivity.MovieDetails> cb);

        @FormUrlEncoded
        @POST("/menuApi/menuTypes")
        public void getMenuTypes(@Field("outlet_id") int outletId, Callback<List<MenuAPI.MenuTypes>> cb);

        @FormUrlEncoded
        @POST("/menuApi/menuGroups")
        public void getMenuGroups(@Field("mt_id") int menuTypeId, Callback<List<MenuAPI.MenuGroup>> cb);

        @FormUrlEncoded
        @POST("/menuApi/menuItems")
        public void getMenuItems(@Field("mgm_id") int menuGroupId, Callback<List<MenuAPI.MenuItem>> cb);
    }



    /*******************************************
     * User Registeration API
     *******************************************/


    public void login(User.UserAccountRequest request, Callback<User.UserAccountResponse> cb) {
        mUserWebServiceAdapater.login(request, cb);
    }

    public void register(User.UserAccountRequest request, Callback<User.UserAccountResponse> cb) {
        mUserWebServiceAdapater.register(request, cb);
    }

    // User credentials related API
    private interface UserAPIWebService {
        @POST("/register")
        public void register(@Body User.UserAccountRequest request, Callback<User.UserAccountResponse> cb);

        @POST("/login")
        public void login(@Body User.UserAccountRequest request, Callback<User.UserAccountResponse> cb);
    }
}
