package io.mo.gendata.builtin;

import java.util.Random;

public class Commontils {
    public static String[] province = {"广东","山东","江苏","河南","河北","浙江","山西","陕西","湖南","福建","云南","四川","安徽","海南","江西","湖北","山西","辽宁","台湾","黑龙江","贵州","甘肃","青海",
                                       "广东省","山东省","江苏省","河南省","河北省","浙江省","山西省","陕西省","湖南省","福建省","云南省","四川省","山东省","安徽省","海南省","江西省","湖北省","江西省","辽宁省","台湾省","黑龙江省","贵州省","甘肃省","青海省",
                                       "重庆","北京","上海","天津",
                                       "北京市","上海市","重庆市","天津市",
                                       "内蒙古","广西","西藏","宁夏","新疆",
                                       "内蒙古自治区","广西壮族自治区","西藏自治区","宁夏回族自治区","新疆维吾尔自治区",
                                       "香港","香港特别行政区","澳门","澳门特别行政区"};
    public static String[] nationality = {"汉族","蒙古族","回族","藏族","维吾尔族","苗族","彝族","壮族","布依族","朝鲜族","满族","侗族","瑶族","白族","土家族","哈尼族","哈萨克族","傣族","黎族","傈僳族",
                                         "佤族","畲族","高山族","拉祜族","水族","东乡族","纳西族","景颇族","柯尔克孜族","土族","达斡尔族","仫佬族","羌族", "布朗族","撒拉族","毛南族","仡佬族","锡伯族","阿昌族","普米族","塔吉克族","怒族",
                                         "乌孜别克族","俄罗斯族","鄂温克族","德昂族","保安族","裕固族","京族","塔塔尔族","独龙族","鄂伦春族","赫哲族","门巴族","珞巴族","基诺族",
                                         "汉","蒙古","回","藏","维吾尔","苗","彝","壮","布依","朝鲜","满","侗","瑶","白","土家","哈尼","哈萨克","傣","黎","傈僳",
                                         "佤","畲","高山","拉祜","水","东乡","纳西","景颇","柯尔克孜","土","达斡尔","仫佬","羌", "布朗","撒拉","毛南","仡佬","锡伯","阿昌","普米","塔吉克","怒",
                                         "乌孜别克","俄罗斯","鄂温克","德昂","保安","裕固","京","塔塔尔","独龙","鄂伦春","赫哲","门巴","珞巴","基诺"
                                        };

    public static String[] politiccountenance = {"中共党员","中共预备党员","共青团员","民革会员","民盟盟员","民建会员","民进会员","农工党党员","致公党党员","九三学社社员","台盟盟员","无党派人士","群众","党员","预备党员","团员"};

    public static String[] sexual = {"男","女","F","M","Male","Femal","MALE","FEMAL"};

    public static String[] country = {"阿富汗","奥兰群岛(芬兰属)","阿尔巴尼亚","阿尔及利亚","美属萨摩亚","安道尔","安哥拉","安圭拉岛","南极洲","安提瓜和巴布达","阿根廷","亚美尼亚","阿鲁巴","澳大利亚","奥地利","阿塞拜疆","巴哈马","巴林","孟加拉国","巴巴多斯","白俄罗斯","比利时","伯利兹","贝宁","百慕大群岛","不丹","玻利维亚","波斯尼亚和黑塞哥维那","博茨瓦纳","布韦岛","巴西","英属印度洋领地","文莱","保加利亚","布基纳法索","布隆迪","柬埔寨","喀麦隆","加拿大","佛得角","开曼群岛","中非共和国","乍得","智利","中国","圣诞岛",
            "科科斯群岛（基灵群岛）","哥伦比亚","科摩罗","刚果","刚果民主共和国","库克群岛","哥斯达黎加","科特迪瓦","克罗地亚","古巴","塞浦路斯","捷克共和国","丹麦","吉布提","多米尼加","多米尼加共和国","厄瓜多尔","埃及","萨尔瓦多","赤道几内亚","厄立特里亚","爱沙尼亚","埃塞俄比亚","福克兰群岛(马尔维纳斯群岛)","法罗群岛","斐济群岛","芬兰","法国","法属圭亚那","法属波利尼西亚","法属南极地区","加蓬","冈比亚","乔治亚","德国","加纳","直布罗陀","希腊","格陵兰","格林纳达","瓜德罗普岛","关岛","危地马拉",
            "格恩西","几内亚","几内亚比绍","圭亚那","海地","赫德和麦克唐纳群岛","梵蒂冈城","洪都拉斯","中国香港","匈牙利","冰岛","印度","印度尼西亚","伊朗","伊拉克","爱尔兰","马恩岛","以色列","意大利","牙买加","日本","泽西","约旦","哈萨克斯坦","肯尼亚","基里巴斯","朝鲜","韩国","科威特","吉尔吉斯斯坦","老挝","拉脱维亚","黎巴嫩","莱索托","利比里亚","利比亚","列支敦士登","立陶宛","卢森堡","中国澳门","马其顿,前南斯拉夫共和国","马达加斯加","马拉维","马来西亚","马尔代夫","马里","马耳他","马绍尔群岛",
            "马提尼克岛","毛里塔尼亚","毛里求斯","马约特岛","墨西哥","密克罗尼西亚","摩尔多瓦","摩纳哥","蒙古","门的内哥罗(黑山)","蒙特塞拉特","摩洛哥","莫桑比克","缅甸","纳米比亚","瑙鲁","尼泊尔","荷兰","荷属安的列斯群岛","新喀里多尼亚","新西兰","尼加拉瓜","尼日尔","尼日利亚","纽埃","诺福克岛","北马里亚纳群岛","挪威","阿曼","巴基斯坦","帕劳群岛","巴勒斯坦当局","巴拿马","巴布亚新几内亚","巴拉圭","秘鲁","菲律宾","皮特克恩群岛","波兰","葡萄牙","波多黎各","卡塔尔","留尼汪岛","罗马尼亚","俄罗斯","卢旺达",
            "圣巴泰勒米","圣赫勒拿岛","圣基茨和尼维斯","圣卢西亚","圣马丁","圣皮埃尔岛和密克隆岛","圣文森特和格林纳丁斯","萨摩亚","圣马力诺","圣多美和普林西比","沙特阿拉伯","塞内加尔","塞尔维亚","塞舌尔","塞拉利昂","新加坡","斯洛伐克","斯洛文尼亚","所罗门群岛","索马里","南非","南乔治亚和南桑德威奇群岛","西班牙","斯里兰卡","苏丹","苏里南","斯瓦尔巴群岛和扬马延","斯威士兰","瑞典","瑞士","叙利亚","中国台湾","塔吉克斯坦","坦桑尼亚","泰国","东帝汶","多哥","托克劳","汤加","特立尼达和多巴哥","突尼斯","土耳其",
            "土库曼斯坦","特克斯群岛和凯科斯群岛","图瓦卢","乌干达","乌克兰","阿拉伯联合酋长国","英国","美国","美属小奥特兰群岛","乌拉圭","乌兹别克斯坦","瓦努阿图","委内瑞拉","越南","维尔京群岛（英属）","维尔京群岛","瓦利斯群岛和富图纳群岛","西撒哈拉","也门","赞比亚","津巴布韦"};

    public static String[] school = {"大学","学院","学校","小学","初中","高中","中学"};
    public static String[] college_sufix = {"学院","系",""};
    public static String[] college_prefix = {"哲学与艺术","哲学与艺术","经济","经济","经济","经济","经济","法","马列部","文学与新闻","外语","外语","外语","文学与新闻","文学与新闻","文学与新闻","文学与新闻","哲学与艺术","哲学与艺术","哲学与艺术","历史文学","历史文学","历史文学","数学","数学",
"物理科学与工程","物理科学与工程","化学","化学","生命科学","生命科学","电子信息","电气信息","电子信息","材料科学与工程","材料科学与工程","环境科学与工程学科","生命科学","数学","材料科学与工程","材料科学与工程","材料科学与工程","制造科学与工程","制造科学与工程",
"制造科学与工程","","制造科学与工程","水利水电工程","电气信息","电气信息","电子信息","电气信息","计算机","电子信息","材料科学与工程","建筑","建筑","建筑","建筑","水利水电工程","水利水电工程","环境科学与工程","化学工程","化学工程","轻工与食品工程","轻工与食品工程",
"纺织工程与服装","建筑","化学工程","水利水电","管理","历史文化","经济","管理","管理","哲学与艺术"};

    public static String[] qualification = {"小学","初中","初级中学","高级中学","高中","中专","职校","中技","专科","本科","硕士","博士","博士后"};
    public static String[] degree = {"学士","硕士","博士"};

    public static String[]  maritalstatus  = {"未婚","已婚","丧偶","离异","初婚有配偶","再婚有配偶","复婚有配偶","初婚","再婚","复婚"};

    public static String[] religion = {"基督教","伊斯兰教","佛教","印度教","锡克教","唯心教","犹太教","耆那教","道教","波斯教","新异教","知觉教"};

    public static String[] _char_num = { "A", "B", "C", "D", "E","_", "F", "G","_","0", "1", "2", "3", "4", "5", "6", "7", "8", "9","H", "I", "J", "K", "L","_", "M", "N", "O", "P", "Q", "R", "S", "_","T", "U", "V", "W", "X", "_","Y", "Z","_",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "_","k", "l","_", "m","0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "n", "o", "p", "_","q", "r", "s", "t", "u", "v", "w", "x", "y", "z","_","0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static String[] _char = {"A", "B", "C", "D", "E","_", "F", "G","H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S","T", "U", "V", "W", "X", "_","Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l","m","n", "o", "p", "_","q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public static String[] _num = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String[] province_code = {"11","12","13","14","15","21","22","23","21","31","32","33","34","35","36","37","41","42","43","44","45","46","50","51","52","53","54","61","62","63","64","65"};

    public static String[] hkphone_prefix = { "21", "22", "23", "24", "25", "26", "27", "28", "29", "31","34","35","36","37","39","51","52","53","54","55","56","59",
                                              "60","61","62","63","64","65","66","67","68","69","90","91","92","93","94","95","96","97","98",};
    public static String[] passport = {"E","SE","DE","PE"};

    public static String[] hkmapass = {"C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C",
                                       "W","CA","CB","CC","CD","CE","CF","CG","CH","CI","CJ","CK","CL","CM","CN","CO","CP","CQ","CR","CS","CT","CU","CV","CW","CX","CY","CZ"};
    public static String[] officalcard = {"南","北","沈","兰","成","济","广","参","证","后","装","海","空"};

    public static String[] swiftcode = {"PBOCCNBJ","EIBCCNBJ","ADBNCNBJ","ICBKCNBJ","ABOCCNBJ","BKCHCNBJ","PCBCCNBJ","COMMCNSH","SDBCCNBJ","PSBCCNBJ","EVERCNBJ","MSBCCNBJ","CMBCCNBS","FJIBCNBA","CIBKCNBJ","HXBKCNBJ","SPDBCNSH","SZDBCNBS","GDBKCN22","CHBHCNBT","HFBACNSD","ZJCBCN2N","BJCNCNBJ","NJCBCNBN","BKNBCN2N",
            "ANBKCNBA","BTCBCNBJ","CHCCCNSS","CBOCCNBC","CTGBCN22","CQCBCN22","DLCBCNBD","DDBKCNBD","DECCCNBD","DGCBCN22","DYSHCNBJ","KCCBCN2K","FZCBCNBS","FXBKCNBJ","BKGZCNBN","GDHBCN22","BGBKCNBJ","GZCBCN22","GLBKCNBG","GYCBCNSI","HCCBCNBH","BKHDCNB1","WHCBCNBN","HZCBCN2H","BKSHCNBJ",
            "HFCBCNSH","JLBKCNBJ","BKJNCNBJ","BOJXCNBJ","BOJSCNBN","JHCBCNBJ","JSHBCNBJ","JZCBCNBJ","BOJJCNBJ","CKLBCNBJ","LWCBCNBJ","LZCBCNBL","BOLFCNBL","LSCCCNBL","LCOMCNBJ","LYCBCNBL","LZBKCNBJ","LJBCCNBH","BOLYCNB1","NCCKCNBN","NCCCCNBD","HSSYCNBH","DHBCCN2N","BINHCN2N","YCCBCNBY",
            "JNSHCNBN","ZBBKCNBZ","QCCBCNBQ","BOXNCNBL","BKQZCNBZ","RZCBCNBD","BOSHCNSH","SXCBCN2X","SYCBCNBY","DWRBCNSU","TZBKCNBT","BOTSCNBJ","TCCBCNBT","WFCBCNBN","WHBKCNBJ","WZCBCNSH","UCCBCNBJ","IXABCNBX","IBXHCNBA","CBXMCNBA","YTCBCNSD","YKCBCNBJ","CZCBCN2X","ZJMTCNSH","ZJTLCNBH",
            "ZZBKCNBZ","CITICNSX","CHASCN22","MSBKCN22","BOFACN","BOFMCNBJ","ROYCCNBJ","UBSWCNBJ","CRESCNSH","BARCCNSH","DEUTCNBJ","NOLACNSH","BCITCNSH","SGCLCNBJ","BNPACN","CRLYCNSH","NATXCNSH","KREDCNSX","INGBCNSH","RABOCNSH","ABNACNSH","ESSECNSH","ANZBCNSH","WPACCNSX","HSBCCNSH","BEASCNSH",
            "HASECNSH","SCBLCNSX","CIYUCNBA","WIHBCNBX","DSBACNBX","CBNBCN2N","BKKBCNSH","ZXBCCNSH","COXICNBA","MBTCCN","DBSSCNSH","OCBCCNSH","UOVBCNSH","NYCBCNSH","BARBCN22","BOTKCNSH","SMBCCN","MHCBCNSH","HNBNCNBJ","SHBKCNBJ","CZNBCNBJ","HVBKCNBJ","IBKOCNBT","KODBCN","KOEXCN","EWBKCNSH",
            "FSBCCNSH","CNMBCNBS","BOFOCNBA","SSVBCNSH"};

    public static String getRandomProvince(){
        int index = (int)(Math.random()*province.length);
        return province[index];
    }

    public static String getRandomNationality(){
        int index = (int)(Math.random()*nationality.length);
        return nationality[index];
    }

    public static String getRandomPoliticCountenance(){
        int index = (int)(Math.random()*politiccountenance.length);
        return politiccountenance[index];
    }

    public static String getRandomSex(){
        int index = (int)(Math.random()*sexual.length);
        return sexual[index];
    }

    public static String getRandomCountryName(){
        int index = (int)(Math.random()*country.length);
        return country[index];
    }

    public static String getRandomHKphoneNum(){
        StringBuffer sb = new StringBuffer();

        Random r = new Random();
        int first_num = r.nextInt(hkphone_prefix.length);
        sb.append(hkphone_prefix[first_num]);
        for(int i=0;i<6;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomQQNum(){
        StringBuffer sb = new StringBuffer();
        String first = "123456789";
        String other = "0123456789";
        int count = (int)(Math.random()*15);
        if (count < 5) count += 5;

        Random r = new Random();
        int first_num = r.nextInt(first.length());
        sb.append(first.charAt(first_num));
        for(int i=0;i<count;i++){
            int num = r.nextInt(other.length());
            sb.append(other.charAt(num));
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomWechatNum(){
        StringBuffer sb = new StringBuffer();
        int count = (int)(Math.random()*20);
        if (count < 5) count += 5;

        Random r = new Random();
        int first_num = r.nextInt(_char.length);
        sb.append(_char[first_num]);
        for(int i=0;i<count;i++){
            int num = r.nextInt(_char_num.length);
            sb.append(_char_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomDriveLicenseNum(){
        StringBuffer sb = new StringBuffer();

        Random r = new Random();
        int first_num = r.nextInt(province_code.length);
        sb.append(province_code[first_num]);
        for(int i=0;i<10;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }



    public static String getRandomPassportNum(){
        StringBuffer sb = new StringBuffer();

        Random r = new Random();
        int first_num = r.nextInt(province_code.length);
        sb.append(province_code[first_num]);
        for(int i=0;i<10;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomSchoolName(String prefix){
        int index = (int)(Math.random()*school.length);
        return prefix + school[index];
    }

    public static String getRandomCollegeName(){
        int p1 = (int)(Math.random()*college_prefix.length);
        int p2 = (int)(Math.random()*college_sufix.length);
        return college_prefix[p1] + college_sufix[p2];
    }

    public static String getRandomQualification(){
        int index = (int)(Math.random()*qualification.length);
        return qualification[index];
    }

    public static String getRandomDegree(){
        int index = (int)(Math.random()*degree.length);
        return degree[index];
    }

    public static String getRandomMaritalstatus(){
        int index = (int)(Math.random()*maritalstatus.length);
        return maritalstatus[index];
    }

    public static String getRandomReligion(){
        int index = (int)(Math.random()*religion.length);
        return religion[index];
    }

    public static String getRandomPassport(){
        StringBuffer sb = new StringBuffer();
        int count = 7 ;
        Random r = new Random();
        int first_num = r.nextInt(passport.length);
        sb.append(passport[first_num]);
        if(passport[first_num].equalsIgnoreCase("E"))
            count = 8;
        for(int i=0;i<count;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomPassportHK(){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        sb.append("K");
        for(int i=0;i<8;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomPassportMA(){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        sb.append("MA");
        for(int i=0;i<7;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomHKMAPass(){
        StringBuffer sb = new StringBuffer();
        int count = 7 ;
        Random r = new Random();
        int first_num = r.nextInt(hkmapass.length);
        sb.append(hkmapass[first_num]);
        if(hkmapass[first_num].equalsIgnoreCase("C") || hkmapass[first_num].equalsIgnoreCase("W"))
            count = 8;
        for(int i=0;i<count;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

    public static String getRandomOfficalCardNum(){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int first_num = r.nextInt(officalcard.length);
        sb.append(officalcard[first_num]+"字第");
        for(int i=0;i<8;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        sb.append("号");
        return sb.toString();
    }

    public static String getRandomSwiftCode(){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        int first_num = r.nextInt(swiftcode.length);
        sb.append(swiftcode[first_num]);
        for(int i=0;i< 3;i++){
            int num = r.nextInt(_num.length);
            sb.append(_num[num]);
            //str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }


    public static void main(String args[]){
        System.out.println(getRandomHKphoneNum());
    }
}
