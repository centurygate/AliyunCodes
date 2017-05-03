package JasonTest.JasonTest;

public class Measure extends MeasureKey {

    private Double value;
    

    private Double positionX;

    private Double positionY;
    

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
    
    
    public Double getPositionX(){ return positionX; }

    public void setPositionX(Double positionX){ this.positionX = positionX; }


    public Double getPositionY(){ return positionY; }

    public void setPositionY(Double postionY){ this.positionY = postionY; }
    
    

    @Override
    public String toString()
    {
        return "Measure{" +
                "value=" + value +
                "} " + super.toString();
    }
}
