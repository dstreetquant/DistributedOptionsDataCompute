package com.ritesh.optionstrategies.pricingmodel;

public class BlackScholesPricingModel {
	
	private BlackScholesPricingModel(){
		//Empty Constructor
	}

	/**
	 * The Black and Scholes (1973) Stock option formula
	 * @param CallPutFlag - CALL or PUT
	 * @param S - Stock Price
	 * @param X - Strike Price of option
	 * @param T - Time until expiration, expressed as a percent of a year
	 * @param r - Risk-free Rate
	 * @param v - Implied Volatility
	 * @return - option price value
	 */
	public static double calculateBlackScholes(char CallPutFlag, double S, double X, double T, double r, double v) {
		double d1, d2;

		d1 = (Math.log(S / X) + (r + v * v / 2) * T) / (v * Math.sqrt(T));
		d2 = d1 - v * Math.sqrt(T);

		if (CallPutFlag == 'c') {
			//Call Option Price
			return S * CND(d1) - X * Math.exp(-r * T) * CND(d2);
		} else {
			//Put Option Price
			return X * Math.exp(-r * T) * CND(-d2) - S * CND(-d1);
		}
	}
	
	/**
	 * Calculation of BSM Greeks
	 * @param S
	 * @param X
	 * @param T
	 * @param r
	 * @param v
	 * @return
	 */
	private static double[] calculateBlackScholesGreeks(double S, double X, double T, double r, double v){
		double[] greeks = new double[5];
		double d1, d2;

		d1 = (Math.log(S / X) + (r + v * v / 2) * T) / (v * Math.sqrt(T));
		d2 = d1 - v * Math.sqrt(T);
		
		double delta = CND(d1);
		greeks[0] = delta;
		
		double gamma = CND(d1)/(S * v * Math.sqrt(T));
		greeks[1] = gamma;
		
		double theta = - (S * v * CND(d1))/(2 * Math.sqrt(T)) - r * X * Math.log(-r * T) * CND(d2);
		greeks[2] = theta;
		
		double vega = S * Math.sqrt(T) * CND(d1);
		greeks[3] = vega;
		
		double rho = X * T * Math.log(-r * T) * CND(d2);
		greeks[4] = rho;
		
		return greeks;
	}
	
	private static double calculateBlackScholesVolatility(double S, double X, double T, double r, double optPrice){
		
	}

	/**
	 *  The cumulative normal distribution function
	 * @param d
	 * @return
	 */
	private static double CND(double d) {
		double L, K, w;
		double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;

		L = Math.abs(d);
		K = 1.0 / (1.0 + 0.2316419 * L);
		w = 1.0 - 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L * L / 2)
				* (a1 * K + a2 * K * K + a3 * Math.pow(K, 3) + a4 * Math.pow(K, 4) + a5 * Math.pow(K, 5));

		if (d < 0.0) {
			w = 1.0 - w;
		}
		return w;
	}
}
