package io.mo.gendata.builtin;

import java.util.Random;

public class CarUtils {
    public CarUtils() {} // 默认不要实例化
    // 车牌号开头
    private static String[] a = {
            "京A","京C","京E","京F","京H","京G","京B","津A","津B", "津C", "津E",
            "沪A", "沪B", "沪D", "沪C","渝A", "渝B", "渝C", "渝G", "渝H","冀A",
            "冀B", "冀C", "冀D", "冀E", "冀F", "冀G", "冀H", "冀J", "冀R", "冀T",
            "豫A", "豫B", "豫C", "豫D", "豫E", "豫F","豫G","豫H","豫J","豫K","豫L",
            "豫M","豫N","豫P","豫Q","豫R","豫S","豫U","云A", "云C", "云D", "云E", "云F",
            "云G","云H","云J","云K","云L","云M","云N","云P","云Q","云R ","云S",
            "辽A", "辽B", "辽C", "辽D", "辽E", "辽F","辽G","辽H","辽J","辽K","辽L","辽M",
            "辽N","辽P","辽V","黑A", "黑B", "黑C", "黑D", "黑E", "黑F","黑G","黑H","黑J","黑K",
            "黑L","黑M","黑N","黑P","黑R","湘A", "湘B", "湘C", "湘D", "湘E", "湘F","湘G","湘H","湘J",
            "湘K","湘L","湘M","湘N","湘U","湘S","皖A", "皖B", "皖C", "皖D", "皖E", "皖F","皖G","皖H","皖J",
            "皖K","皖L","皖M","皖N","皖P","皖Q","皖R","皖S","鲁A", "鲁B", "鲁C", "鲁D", "鲁E", "鲁F","鲁G",
            "鲁H","鲁J","鲁K","鲁L","鲁M","鲁N","鲁P","鲁Q","鲁R","鲁S","鲁U","鲁V","鲁Y","新A", "新B", "新C",
            "新D", "新E", "新F","新G","新H","新J","新K","新L","新M","新N","新P","新Q","新R","苏A", "苏B", "苏C",
            "苏D", "苏E", "苏F","苏G","苏H","苏J","苏K","苏L","苏M","苏N","浙A", "浙B", "浙C", "浙D", "浙E", "浙F",
            "浙G","浙H","浙J","浙K ","浙L","赣A","赣B","赣C","赣D","赣E","赣F","赣G","赣H","赣J","赣K","赣L","赣M","鄂A",
            "鄂B","鄂C","鄂D","鄂E","鄂F","鄂G","鄂H","鄂J","鄂K" ,"鄂L","鄂M","鄂N","鄂P","鄂Q","鄂R","鄂S","桂A","桂B",
            "桂C","桂D","桂E","桂F","桂G","桂H","桂J","桂K","桂L","桂M","桂N","桂P" ,"桂R","甘A","甘B","甘C","甘D","甘E",
            "甘F","甘G","甘H","甘J","甘K","甘L","甘M" ,"甘N","甘P","晋A" ,"晋B","晋C","晋D","晋E","晋F","晋H","晋J","晋K",
            "晋L","晋M","蒙A","蒙B","蒙C","蒙D","蒙E","蒙F","蒙G","蒙H","蒙J","蒙K","蒙L","蒙M","陕A","陕B","陕C","陕D","陕E",
            "陕F","陕G","陕H","陕J","陕K","陕U","陕V","吉A","吉B","吉C","吉D","吉E","吉F","吉G","吉H","吉J","闽A","闽B","闽C",
            "闽D","闽E","闽F" ,"闽G","闽H","闽J","闽K","贵A","贵B" ,"贵C","贵D","贵E" ,"贵F","贵G","贵H","贵J","粤A","粤B","粤C",
            "粤D","粤E","粤F","粤G","粤H","粤J","粤K","粤L","粤M","粤N","粤P","粤Q","粤R","粤S","粤T","粤U","粤V","粤W","粤X","粤Y",
            "粤Z","青A","青B","青C","青D","青E","青F","青G","青H","藏A","藏B","藏C","藏D","藏E","藏F","藏G","藏H" ,"藏J","川A","川B",
            "川C","川D","川E","川F","川H","川J","川K","川L","川M","川Q","川R","川S","川T","川U","川V","川W","川X","川Y","川Z","宁A",
            "宁B","宁C","宁D","琼A","琼B","琼C","琼D","琼E"
    };
    private static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N",  "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    private static String[] car_type = {"普通乘用车","活顶乘用车","高级乘用车","小型乘用车","敞篷车","仓背乘用车","旅行车","多用途乘用车","短头乘用车","越野乘用车","防弹车","旅居车",
            "殡仪车","救护车","小型客车","城市客车","长途客车","旅游客车","铰接客车","无轨电车","越野客车","专用客车","半挂牵引车","普通货车","多用途货车","全挂牵引车","专用作业车","专用货车","越野货车"
    };


    private static String[] car_brand = {};

    private static String[] fuel_type = {"柴油","汽油","无铅汽油","乙醇汽油"};

    private static String[] fuel_identification = {"90号","92号","93号","95号","97号","98号","100号"};

    private static String[] engine_num = {""};

    /**车架号地区代码数组*/

    public static final String areaArray[] = new String[]{"1", "2", "3", "6", "9", "J", "K", "L", "R", "S", "T", "V", "W", "Y", "Z", "G"};

    /**车架号中可能出现的字符数组*/

    public static final String charArray[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "V", "W", "X", "Y"};

    /**车架号校验位计算数组*/

    public static final Object[][] KVMACTHUP = new Object[][]{{'A', 1}, {'B', 2}, {'C', 3}, {'D', 4}, {'E', 5}, {'F', 6},{'G', 7}, {'H', 8}, {'I', 0}, {'J', 1}, {'K', 2}, {'L', 3},{'M', 4}, {'N', 5}, {'O', 0}, {'P', 7}, {'Q', 8}, {'R', 9}, {'S', 2}, {'T', 3}, {'U', 4}, {'V', 5}, {'W', 6}, {'X', 7},{'Y', 8}, {'Z', 9}};

    /**车架号数据加权数组*/

    public static final int[] WEIGHTVALUE = new int[]{8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};


    /**
     * 随机生成车牌号
     * @return
     */
    public static String getCarPlateNumber() {
        int count = 0;
        String sb = "";
        // 随机获取后五位随机序号
        while (count < 5) {
            Random random = new Random();
            String str2 = b[random.nextInt(26)];
            int num = random.nextInt(10);
            int j = 0;
            // 控制字母和数字的数量
            if (count > 2) {
                for (int i = 0; i < sb.length(); i++) {
                    char c = sb.charAt(i);
                    if ((c >= 'A') && (c <= 'Z')) {
                        j++;
                    }
                }
            }
            if (j < 2) {
                // 字母与数字的概率相同
                if (random.nextBoolean()) {
                    sb += num;
                } else {
                    sb += str2;
                }
                count++;
            } else {
                sb += num;
                count++;
            }
        }
        // 获取发证机关的随机号
        Random random1 = new Random();
        int num1 = random1.nextInt(31);
        String plate = a[num1] + sb;
        return plate.matches("^.*[A-Z]{3}.*$") ? getCarPlateNumber() : plate; // 判断是否符合车牌规范，如果开头有连续的字母，则重新获取
    }

    public static String getIsuredCode(String vin) {

        char[] Vin = vin.toCharArray();

        int sum = 0,tempValue = 0;

        char temp;

        for (int i = 0; i < 17; i++) {

            if (Vin[i] >= 'a' && Vin[i] <= 'z') {

                temp = (char) (Vin[i] - 32);

            } else if ((Vin[i] >= 'A') && (Vin[i] <= 'Z')) {

                temp = Vin[i];

            } else if ((Vin[i] >= '0') && (Vin[i] <= '9')) {

                tempValue = Integer.parseInt(String.valueOf(Vin[i]));

                temp = Vin[i];

            } else {

                return "ERROR";

            }

            if ((temp >= 'A') && (temp <= 'Z')) {

                for (int j = 0; j < 26; j++) {

                    if (temp == (Character) KVMACTHUP[j][0]) {

                        tempValue = (Integer) KVMACTHUP[j][1];

                    }

                }

            }

            sum += tempValue * WEIGHTVALUE[i];

        }

        int reslt = sum % 11;

        if (reslt != 10) {

            return String.valueOf(reslt);

        } else {

            return "X";

        }

    }


    /**

     * 判断vin是否正确

     * @param vin

     * @return

     */

    public static boolean isVin(String vin) {

        String isuredCode = getIsuredCode(vin);

        if (vin.substring(8, 9).equals(isuredCode)) {

            return true;

        } else {

            return false;

        }

    }

    /**

     * 拼接车架号

     *

     * @param beforeStr

     * @param afterStr

     * @return

     */

    public static String spellVin(String beforeStr, String afterStr) {

        StringBuilder vinBuffer = new StringBuilder();

        String preVin = vinBuffer.append(beforeStr).append("X").append(afterStr).toString();

        String isuredCode = getIsuredCode(preVin);

        String vin = new StringBuilder(beforeStr).append(isuredCode).append(afterStr).toString();

        if (isVin(vin)) {

            return vin;

        } else {

            return spellVin(beforeStr, afterStr);

        }

    }

    /**

     * 生成随机前缀

     *

     * @return

     */

    public static String prepareBeforeStr() {

        StringBuilder StringBuilder = new StringBuilder();

        //StringBuilder.append("VNN");

        for (int i = 0; i < 8; i++) {

            StringBuilder.append(getRandomChar(areaArray));

        }

        return StringBuilder.toString();

    }

    /**

     * 生成随机后缀

     *

     * @return

     */

    public static String prepareAfterStr() {

        StringBuilder StringBuilder = new StringBuilder();

        for (int i = 0; i < 3; i++) {

            StringBuilder.append(getRandomChar(charArray));

        }

        StringBuilder.append(prepareNo());

        return StringBuilder.toString();

    }

    /**

     * 生成随机的生产序号

     * @return

     */

    public static String prepareNo(){

        Random random = new Random();

        StringBuilder numStrBuff = new StringBuilder();

        for(int i=0;i<5;i++){

            numStrBuff.append(Integer.toHexString(random.nextInt(16)).toUpperCase());

        }

        return numStrBuff.toString();

    }

    /**

     * 返回随机字符

     * @return

     */

    public static String getRandomChar(Object array[]) {

        return charArray[(int) (Math.random() * 100 % array.length)];

    }

    /**

     * 获取随机的车架号

     * @return

     */

    public static String getRandomCarVin() {

        String beforeStr = prepareBeforeStr();

        String afterStr = prepareAfterStr();

        String vin = spellVin(beforeStr, afterStr);

        return vin;

    }


    public static void main(String[] args){
        System.out.println(getCarPlateNumber());
        System.out.println(getRandomCarVin());
    }

}
