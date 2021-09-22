package net.jhorstmann.parquetwriterexample;

import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopOutputFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.avro.Schema.Type.*;

public class Main {

	private static final Object DEFAULT_NULL = null;
	private static final String NAMESPACE = "net.jhorstmann.parquetwriterexample";

	private static final Schema TIMESTAMP_MILLIS = LogicalTypes.timestampMillis().addToSchema(Schema.create(LONG));

	private static Schema nullable(Schema schema) {
		return Schema.createUnion(Schema.create(NULL), schema);
	}

	private static Schema nullable(Schema.Type type) {
		return nullable(Schema.create(type));
	}

	public static void main(String[] args) throws IOException {

		List<Schema.Field> fields = new ArrayList<>(Arrays.asList(
			new Schema.Field("required_string", Schema.create(STRING), null, DEFAULT_NULL),
			new Schema.Field("optional_string", nullable(STRING), null, DEFAULT_NULL),
			new Schema.Field("required_double", nullable(DOUBLE), null, DEFAULT_NULL),
			new Schema.Field("optional_double", nullable(DOUBLE), null, DEFAULT_NULL),
			new Schema.Field("required_timestamp", nullable(TIMESTAMP_MILLIS), null, DEFAULT_NULL),
			new Schema.Field("optional_timestamp", nullable(TIMESTAMP_MILLIS), null, DEFAULT_NULL),
			new Schema.Field("required_bool", nullable(BOOLEAN), null, DEFAULT_NULL),
			new Schema.Field("optional_bool", nullable(BOOLEAN), null, DEFAULT_NULL)
		));


		Schema schema = Schema.createRecord("record", null, NAMESPACE, false, fields);

		Configuration conf = new Configuration();
		final HadoopOutputFile outputFile = HadoopOutputFile.fromPath(new Path("target/output.parquet"), conf);

		try (ParquetWriter<GenericRecord> parquetWriter = AvroParquetWriter.<GenericRecord>builder(outputFile)
			.withSchema(schema)
			.withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
			.withCompressionCodec(CompressionCodecName.GZIP)
			.build()) {

			GenericData.Record record = new GenericData.Record(schema);
			record.put(0, "required_string1");
			record.put(1, "optional_string");
			record.put(2, 1.0);
			record.put(3, 1.0);
			record.put(4, 12345678);
			record.put(5, 12345678);
			record.put(6, true);
			record.put(7, false);

			parquetWriter.write(record);

		}

	}
}
