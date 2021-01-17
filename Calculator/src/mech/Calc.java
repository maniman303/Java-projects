package mech;

import java.math.BigInteger;

public class Calc
{
    private BigInteger res;
    private BigInteger arg;
    private BigInteger ten;
    private BigInteger minus;
    private String op;
    private Boolean error;
    public Calc()
    {
        res = new BigInteger("0");
        arg = new BigInteger("0");
        ten = new BigInteger("10");
        minus = new BigInteger("-1");
        op = "";
        error = false;
    }
    public void addCypher(String c)
    {
        arg = arg.multiply(ten);
        arg = arg.add(new BigInteger(c));
    }
    public void deleteCypher()
    {
        arg = arg.divide(ten);
    }
    public void invertArg()
    {
        arg = arg.multiply(minus);
    }
    public void calculate()
    {
        if (!error) switch (op)
        {
            case "":
                res = arg;
                break;
            case "+":
                res = res.add(arg);
                break;
            case "-":
                res = res.subtract(arg);
                break;
            case "*":
                res = res.multiply(arg);
                break;
            case "/":
                if (arg.compareTo(BigInteger.ZERO) != 0) res = res.divide(arg);
                else
                {
                    error = true;
                    res = BigInteger.ZERO;
                }
                break;
            case "^":
                res = pow(res, arg);
                break;
            case "%":
                res = res.modPow(BigInteger.ONE, arg);
                break;
            case "!":
                res = strong(arg);
                break;
            case "N":
                res = newton(res, arg);
                break;
        }
        else reset();
        arg = new BigInteger("0");
        op = "";
    }
    public void reset()
    {
        res = new BigInteger("0");
        arg = new BigInteger("0");
        op = "";
        error = false;
    }
    public void switchVars()
    {
        if (res.compareTo(BigInteger.ZERO) == 0)
        {
            res = arg;
            arg = new BigInteger("0");
        }
    }
    public Boolean getError() { return error; }
    public String getRes() { return res.toString(); }
    public String getArg() { return arg.toString(); }
    public String getOp() { return op; }
    public void setOp(String s)
    {
        if (error)
        {
            error = false;
            reset();
        }
        op = s;
        if (!op.contains("!")) switchVars();
    }
    private BigInteger pow(BigInteger base, BigInteger e)
    {
        BigInteger r = BigInteger.ONE;
        BigInteger i = e;
        if (i.compareTo(BigInteger.ZERO) < 0)
        {
            r = new BigInteger("0");
            return r;
        }
        while (i.compareTo(BigInteger.ZERO) > 0)
        {
            r = r.multiply(base);
            i = i.subtract(BigInteger.ONE);
        }
        return r;
    }
    private BigInteger strong(BigInteger a)
    {
        if (a.compareTo(BigInteger.ZERO) <= 0) return BigInteger.ONE;
        BigInteger r = BigInteger.ONE;
        BigInteger i = a;
        while (i.compareTo(BigInteger.ZERO) > 0)
        {
            r = r.multiply(i);
            i = i.subtract(BigInteger.ONE);
        }
        return r;
    }
    private BigInteger newton(BigInteger n, BigInteger k)
    {
        BigInteger r;
        BigInteger up = strong(n);
        BigInteger down = strong(k).multiply(strong(n.subtract(k)));
        r = up.divide(down);
        return r;
    }
}
