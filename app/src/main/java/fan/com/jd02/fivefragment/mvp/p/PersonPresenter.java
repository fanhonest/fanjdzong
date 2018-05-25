package fan.com.jd02.fivefragment.mvp.p;


import fan.com.jd02.fivefragment.bean.PersonInfoBean;
import fan.com.jd02.fivefragment.mvp.m.ModelCallBack;
import fan.com.jd02.fivefragment.mvp.m.PersonModel;
import fan.com.jd02.fivefragment.mvp.v.MyView;

/**
 * Created by fxf on 2018.05.23.
 */

public class PersonPresenter {
    PersonModel personModel = new PersonModel();
    MyView.PersonView personView;
    public PersonPresenter(MyView.PersonView personView) {
        this.personView = personView;
    }

    public void getData(int uid) {
        personModel.getPersonData(uid, new ModelCallBack.PersonCallBack() {
            @Override
            public void success(PersonInfoBean personInfoBean) {
                personView.sucess(personInfoBean);
                System.out.println("个人p数据："+personInfoBean.toString());
            }

            @Override
            public void failed(Throwable code) {
                System.out.println("个人p错误："+code);
            }
        });
    }
}
