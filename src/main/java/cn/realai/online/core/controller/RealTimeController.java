package cn.realai.online.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.realai.online.common.vo.ResultCode;
import cn.realai.online.common.vo.ResultMessage;
import cn.realai.online.common.vo.ResultUtils;
import cn.realai.online.core.bussiness.RealTimeBussiness;
import cn.realai.online.core.query.realtime.RealTimeData;  

/**
 * 实时返回行方请求的类
 * @author lyh
 */
@Controller
@RequestMapping("/realTime")
public class RealTimeController {

    private static Logger logger = LoggerFactory.getLogger(RealTimeController.class);

    @Autowired
    public RealTimeBussiness realTimeBussiness;

    public static void main(String[] args) {
    	String realTimeJson = "{'serviceId':410,'personalId':15276,'data':{'hetro':{'Feature_1':null,'Feature_2':null,'Feature_3':3.0,'Feature_4':null,'Feature_5':1.0,'Feature_6':null,'Feature_7':null,'Feature_8':null,'Feature_9':null,'Feature_10':null,'Feature_11':null,'Feature_12':null,'Feature_13':11.0,'Feature_14':null,'Feature_15':null,'Feature_16':null,'Feature_17':null,'Feature_18':null,'Feature_19':null,'Feature_20':null,'Feature_21':null,'Feature_22':null,'Feature_23':null,'Feature_24':null,'Feature_25':null,'Feature_26':null,'Feature_27':null,'Feature_28':null,'Feature_29':null,'Feature_30':null,'Feature_31':null,'Feature_32':null,'Feature_33':null,'Feature_34':1.0,'Feature_35':1.0,'Feature_36':1.0,'Feature_37':1.0,'Feature_38':1.0,'Feature_39':1.0,'Feature_40':1.0,'Feature_41':1.0,'Feature_42':1.0,'Feature_43':1.0,'Feature_44':1.0,'Feature_45':1.0,'Feature_46':1.0,'Feature_47':null,'Feature_48':1356.0,'Feature_49':1.0,'Feature_50':1.0,'Feature_51':1.0,'Feature_52':1.0,'Feature_53':1.0,'Feature_54':1.0,'Feature_55':1.0,'Feature_56':1.0,'Feature_57':1.0,'Feature_58':1.0,'Feature_59':1.0,'Feature_60':1.0,'Feature_61':2.0,'Feature_62':null,'Feature_63':1441.0,'Feature_64':1.0,'Feature_65':1.0,'Feature_66':1.0,'Feature_67':1.0,'Feature_68':1.0,'Feature_69':1.0,'Feature_70':1.0,'Feature_71':1.0,'Feature_72':1.0,'Feature_73':1.0,'Feature_74':1.0,'Feature_75':1.0,'Feature_76':2.0,'Feature_77':null,'Feature_78':1419.0,'Feature_79':1.0,'Feature_80':1.0,'Feature_81':1.0,'Feature_82':1.0,'Feature_83':1.0,'Feature_84':1.0,'Feature_85':1.0,'Feature_86':1.0,'Feature_87':1.0,'Feature_88':1.0,'Feature_89':1.0,'Feature_90':1.0,'Feature_91':3.0,'Feature_92':null,'Feature_93':1441.0,'Feature_94':0.0,'Feature_95':0.0,'Feature_96':0.0,'Feature_97':0.0,'Feature_98':0.0,'Feature_99':0.0,'Feature_100':0.0,'Feature_101':0.0,'Feature_102':0.0,'Feature_103':0.0,'Feature_104':0.0,'Feature_105':0.0,'Feature_106':1.0,'Feature_107':null,'Feature_108':1356.0,'Feature_109':2.0,'Feature_110':2.0,'Feature_111':2.0,'Feature_112':2.0,'Feature_113':2.0,'Feature_114':2.0,'Feature_115':2.0,'Feature_116':2.0,'Feature_117':2.0,'Feature_118':2.0,'Feature_119':2.0,'Feature_120':2.0,'Feature_121':4.0,'Feature_122':null,'Feature_123':1419.0,'Feature_124':2.0,'Feature_125':2.0,'Feature_126':2.0,'Feature_127':2.0,'Feature_128':2.0,'Feature_129':2.0,'Feature_130':2.0,'Feature_131':2.0,'Feature_132':2.0,'Feature_133':2.0,'Feature_134':2.0,'Feature_135':2.0,'Feature_136':6.0,'Feature_137':null,'Feature_138':null,'Feature_139':1.0,'Feature_140':1.0,'Feature_141':1.0,'Feature_142':1.0,'Feature_143':1.0,'Feature_144':1.0,'Feature_145':1.0,'Feature_146':1.0,'Feature_147':1.0,'Feature_148':1.0,'Feature_149':1.0,'Feature_150':1.0,'Feature_151':1.0,'Feature_152':null,'Feature_153':1356.0,'Feature_154':1.0,'Feature_155':1.0,'Feature_156':1.0,'Feature_157':1.0,'Feature_158':1.0,'Feature_159':1.0,'Feature_160':1.0,'Feature_161':1.0,'Feature_162':1.0,'Feature_163':1.0,'Feature_164':1.0,'Feature_165':1.0,'Feature_166':3.0,'Feature_167':null,'Feature_168':1356.0,'Feature_169':1.0,'Feature_170':1.0,'Feature_171':1.0,'Feature_172':1.0,'Feature_173':1.0,'Feature_174':1.0,'Feature_175':1.0,'Feature_176':1.0,'Feature_177':1.0,'Feature_178':1.0,'Feature_179':1.0,'Feature_180':1.0,'Feature_181':3.0,'Feature_182':null,'Feature_183':null,'Feature_184':1.0,'Feature_185':1.0,'Feature_186':1.0,'Feature_187':1.0,'Feature_188':1.0,'Feature_189':1.0,'Feature_190':1.0,'Feature_191':1.0,'Feature_192':1.0,'Feature_193':1.0,'Feature_194':1.0,'Feature_195':1.0,'Feature_196':1.0,'Feature_197':null,'Feature_198':null,'Feature_199':1.0,'Feature_200':1.0,'Feature_201':1.0,'Feature_202':1.0,'Feature_203':1.0,'Feature_204':1.0,'Feature_205':1.0,'Feature_206':1.0,'Feature_207':1.0,'Feature_208':1.0,'Feature_209':1.0,'Feature_210':1.0,'Feature_211':1.0,'Feature_212':null,'Feature_213':1454.0,'Feature_214':1.0,'Feature_215':1.0,'Feature_216':1.0,'Feature_217':1.0,'Feature_218':1.0,'Feature_219':1.0,'Feature_220':1.0,'Feature_221':1.0,'Feature_222':1.0,'Feature_223':1.0,'Feature_224':1.0,'Feature_225':1.0,'Feature_226':2.0,'Feature_227':null,'Feature_228':1657.0,'Feature_229':1.0,'Feature_230':1.0,'Feature_231':1.0,'Feature_232':1.0,'Feature_233':1.0,'Feature_234':1.0,'Feature_235':1.0,'Feature_236':1.0,'Feature_237':1.0,'Feature_238':1.0,'Feature_239':1.0,'Feature_240':1.0,'Feature_241':2.0,'Feature_242':null,'Feature_243':1454.0,'Feature_244':1.0,'Feature_245':1.0,'Feature_246':1.0,'Feature_247':1.0,'Feature_248':1.0,'Feature_249':1.0,'Feature_250':1.0,'Feature_251':1.0,'Feature_252':1.0,'Feature_253':1.0,'Feature_254':1.0,'Feature_255':1.0,'Feature_256':2.0,'Feature_257':null,'Feature_258':null,'Feature_259':1.0,'Feature_260':1.0,'Feature_261':1.0,'Feature_262':1.0,'Feature_263':1.0,'Feature_264':1.0,'Feature_265':1.0,'Feature_266':1.0,'Feature_267':1.0,'Feature_268':1.0,'Feature_269':1.0,'Feature_270':1.0,'Feature_271':1.0,'Feature_272':null,'Feature_273':null,'Feature_274':1.0,'Feature_275':1.0,'Feature_276':1.0,'Feature_277':1.0,'Feature_278':1.0,'Feature_279':1.0,'Feature_280':1.0,'Feature_281':1.0,'Feature_282':1.0,'Feature_283':1.0,'Feature_284':1.0,'Feature_285':1.0,'Feature_286':1.0,'Feature_287':null,'Feature_288':null,'Feature_289':1.0,'Feature_290':1.0,'Feature_291':1.0,'Feature_292':1.0,'Feature_293':1.0,'Feature_294':1.0,'Feature_295':1.0,'Feature_296':1.0,'Feature_297':1.0,'Feature_298':1.0,'Feature_299':1.0,'Feature_300':1.0,'Feature_301':1.0,'Feature_302':null,'Feature_303':null,'Feature_304':1.0,'Feature_305':1.0,'Feature_306':1.0,'Feature_307':1.0,'Feature_308':1.0,'Feature_309':1.0,'Feature_310':1.0,'Feature_311':1.0,'Feature_312':1.0,'Feature_313':1.0,'Feature_314':1.0,'Feature_315':1.0,'Feature_316':1.0,'Feature_317':null,'Feature_318':'n','Feature_319':null,'Feature_320':'n','Feature_321':'n','Feature_322':null,'Feature_323':null,'Feature_324':'n','Feature_325':null,'Feature_326':'RETURNED','Feature_327':1555.0,'Feature_328':1419.0,'Feature_329':0.0,'Feature_330':0.0,'Feature_331':0.0,'Feature_332':0.0,'Feature_333':0.0,'Feature_334':0.0,'Feature_335':0.0,'Feature_336':0.0,'Feature_337':0.0,'Feature_338':0.0,'Feature_339':0.0,'Feature_340':0.0,'Feature_341':1.0,'Feature_342':null,'Feature_343':1419.0,'Feature_344':0.0,'Feature_345':0.0,'Feature_346':0.0,'Feature_347':0.0,'Feature_348':0.0,'Feature_349':0.0,'Feature_350':0.0,'Feature_351':0.0,'Feature_352':0.0,'Feature_353':0.0,'Feature_354':0.0,'Feature_355':0.0,'Feature_356':1.0,'Feature_357':null,'Feature_358':1419.0,'Feature_359':0.0,'Feature_360':0.0,'Feature_361':0.0,'Feature_362':0.0,'Feature_363':0.0,'Feature_364':0.0,'Feature_365':0.0,'Feature_366':0.0,'Feature_367':0.0,'Feature_368':0.0,'Feature_369':0.0,'Feature_370':0.0,'Feature_371':1.0,'Feature_372':null,'Feature_373':1419.0,'Feature_374':0.0,'Feature_375':0.0,'Feature_376':0.0,'Feature_377':0.0,'Feature_378':0.0,'Feature_379':0.0,'Feature_380':0.0,'Feature_381':0.0,'Feature_382':0.0,'Feature_383':0.0,'Feature_384':0.0,'Feature_385':0.0,'Feature_386':1.0,'Feature_387':null,'Feature_388':1442.0,'Feature_389':0.0,'Feature_390':0.0,'Feature_391':0.0,'Feature_392':0.0,'Feature_393':0.0,'Feature_394':0.0,'Feature_395':0.0,'Feature_396':0.0,'Feature_397':0.0,'Feature_398':0.0,'Feature_399':0.0,'Feature_400':0.0,'Feature_401':1.0,'Feature_402':null,'Feature_403':1442.0,'Feature_404':0.0,'Feature_405':0.0,'Feature_406':0.0,'Feature_407':0.0,'Feature_408':0.0,'Feature_409':0.0,'Feature_410':0.0,'Feature_411':0.0,'Feature_412':0.0,'Feature_413':0.0,'Feature_414':0.0,'Feature_415':0.0,'Feature_416':2.0,'Feature_417':null,'Feature_418':1442.0,'Feature_419':0.0,'Feature_420':0.0,'Feature_421':0.0,'Feature_422':0.0,'Feature_423':0.0,'Feature_424':0.0,'Feature_425':0.0,'Feature_426':0.0,'Feature_427':0.0,'Feature_428':0.0,'Feature_429':0.0,'Feature_430':0.0,'Feature_431':2.0,'Feature_432':null,'Feature_433':null,'Feature_434':0.0,'Feature_435':0.0,'Feature_436':0.0,'Feature_437':0.0,'Feature_438':0.0,'Feature_439':0.0,'Feature_440':0.0,'Feature_441':0.0,'Feature_442':0.0,'Feature_443':0.0,'Feature_444':0.0,'Feature_445':0.0,'Feature_446':0.0,'Feature_447':null,'Feature_448':null,'Feature_449':0.0,'Feature_450':0.0,'Feature_451':0.0,'Feature_452':0.0,'Feature_453':0.0,'Feature_454':0.0,'Feature_455':0.0,'Feature_456':0.0,'Feature_457':0.0,'Feature_458':0.0,'Feature_459':0.0,'Feature_460':0.0,'Feature_461':0.0,'Feature_462':null,'Feature_463':1674.0,'Feature_464':0.0,'Feature_465':0.0,'Feature_466':0.0,'Feature_467':0.0,'Feature_468':0.0,'Feature_469':0.0,'Feature_470':0.0,'Feature_471':0.0,'Feature_472':0.0,'Feature_473':0.0,'Feature_474':0.0,'Feature_475':0.0,'Feature_476':3.0,'Feature_477':null,'Feature_478':1542.0,'Feature_479':0.0,'Feature_480':0.0,'Feature_481':0.0,'Feature_482':0.0,'Feature_483':0.0,'Feature_484':0.0,'Feature_485':0.0,'Feature_486':0.0,'Feature_487':0.0,'Feature_488':0.0,'Feature_489':0.0,'Feature_490':0.0,'Feature_491':19.0,'Feature_492':null,'Feature_493':1570.0,'Feature_494':0.0,'Feature_495':0.0,'Feature_496':0.0,'Feature_497':0.0,'Feature_498':0.0,'Feature_499':0.0,'Feature_500':0.0,'Feature_501':0.0,'Feature_502':0.0,'Feature_503':0.0,'Feature_504':0.0,'Feature_505':0.0,'Feature_506':14.0,'Feature_507':null,'Feature_508':1542.0,'Feature_509':0.0,'Feature_510':0.0,'Feature_511':0.0,'Feature_512':0.0,'Feature_513':0.0,'Feature_514':0.0,'Feature_515':0.0,'Feature_516':0.0,'Feature_517':0.0,'Feature_518':0.0,'Feature_519':0.0,'Feature_520':0.0,'Feature_521':5.0,'Feature_522':null,'Feature_523':1546.0,'Feature_524':0.0,'Feature_525':0.0,'Feature_526':0.0,'Feature_527':0.0,'Feature_528':0.0,'Feature_529':0.0,'Feature_530':0.0,'Feature_531':0.0,'Feature_532':0.0,'Feature_533':0.0,'Feature_534':0.0,'Feature_535':0.0,'Feature_536':59.0,'Feature_537':null,'Feature_538':null,'Feature_539':0.0,'Feature_540':0.0,'Feature_541':0.0,'Feature_542':0.0,'Feature_543':0.0,'Feature_544':0.0,'Feature_545':0.0,'Feature_546':0.0,'Feature_547':0.0,'Feature_548':0.0,'Feature_549':0.0,'Feature_550':0.0,'Feature_551':0.0,'Feature_552':null,'Feature_553':1546.0,'Feature_554':0.0,'Feature_555':0.0,'Feature_556':0.0,'Feature_557':0.0,'Feature_558':0.0,'Feature_559':0.0,'Feature_560':0.0,'Feature_561':0.0,'Feature_562':0.0,'Feature_563':0.0,'Feature_564':0.0,'Feature_565':0.0,'Feature_566':68.0,'Feature_567':null,'Feature_568':1542.0,'Feature_569':0.0,'Feature_570':0.0,'Feature_571':0.0,'Feature_572':0.0,'Feature_573':0.0,'Feature_574':0.0,'Feature_575':0.0,'Feature_576':0.0,'Feature_577':0.0,'Feature_578':0.0,'Feature_579':0.0,'Feature_580':0.0,'Feature_581':19.0,'Feature_582':null,'Feature_583':0.0,'Feature_584':0.0,'Feature_585':5.0,'Feature_586':3.0,'Feature_587':3.0,'Feature_588':0.0,'Feature_589':500.0,'Feature_590':1300.0,'Feature_591':400.0,'Feature_592':700.0,'Feature_593':0.0,'Feature_594':0.0,'Feature_595':1542.0,'Feature_596':'RETURNED','Feature_597':'ACH','Feature_598':611.0,'Feature_599':'R08','Feature_600':1542.0,'Feature_601':'RETURNED','Feature_602':'ACH','Feature_603':215.0,'Feature_604':'R08','Feature_605':1555.0,'Feature_606':'RETURNED','Feature_607':'ACH','Feature_608':181.66,'Feature_609':'R08','Feature_610':'RRR','Feature_611':1.0,'Feature_612':3.0,'Feature_613':0.0,'Feature_614':null,'Feature_615':1674.0,'Feature_616':1546.0,'Feature_617':1542.0,'Feature_618':60.0,'Feature_619':1542.0,'Feature_620':'R08','Feature_621':'PaymentStoppedorStopPaymentonItem','Feature_622':1542.0,'Feature_623':1419.0,'Feature_624':3.0,'Feature_625':324.0,'Feature_626':9.0,'Feature_627':1.0,'Feature_628':2.0,'Feature_629':1674.0,'Feature_630':1442.0,'Feature_631':0.0,'Feature_632':null,'Feature_633':null,'Feature_634':'Y','Feature_635':'A1'}}}";
    	RealTimeData realTimeData = JSON.parseObject(realTimeJson, RealTimeData.class);
    	System.out.println(JSON.toJSONString(realTimeData, SerializerFeature.WriteMapNullValue));
	}
    
    @ResponseBody
    @PostMapping(value = "/forecast")
    public String getForecastResult(@RequestBody String realTimeJson) {
    	logger.info("RealTimeController getForecastResult.线上预测请求开始");
    	try {
    		Long startTime = System.currentTimeMillis();
    		RealTimeData realTimeData = JSON.parseObject(realTimeJson, RealTimeData.class);
            if (realTimeData.getServiceId() == null) {
                logger.warn("线上预测传入参数错误, serviceId不能为空");
                return ResultUtils.generateResultStr(ResultCode.DATA_ERROR, ResultMessage.PARAM_ERORR.getMsg(), null);
            }
            String ret = realTimeBussiness.getForecastResult(realTimeData);
            realTimeBussiness.recordRequestInformation(ret, realTimeJson, startTime);
            if ("-1".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.参数错误,部署方式不存在");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_EXPIRED, ResultMessage.OPT_FAILURE.getMsg("参数错误,部署方式不存在"), null);
            }
            if ("-2".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.服务已过期.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_EXPIRED, ResultMessage.OPT_FAILURE.getMsg("服务已过期"), null);
            }
            if ("-3".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.服务没有进行线上部署.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_NO_RELEASE, ResultMessage.OPT_FAILURE.getMsg("服务没有进行线上部署"), null);
            }
            if ("-4".equals(ret)) {
            	logger.error("RealTimeController getForecastResult.线上预测失败,服务请求python计算超时，请稍后重试.");
            	return ResultUtils.generateResultStr(ResultCode.REAL_TIME_TIME_OUT, ResultMessage.OPT_FAILURE.getMsg("服务请求python计算超时，请稍后重试"), null);
            }
            logger.info("RealTimeController getForecastResult.线上预测成功");
            return ResultUtils.generateResultStr(ResultCode.SUCCESS, ResultMessage.OPT_SUCCESS.getMsg(), ret);
        } catch (Exception e) {
        	logger.error("RealTimeController getForecastResult.线上预测失败");
            return ResultUtils.generateResultStr(ResultCode.REAL_TIME_ERROR, ResultMessage.OPT_FAILURE.getMsg(), null);
        }
    }

}
